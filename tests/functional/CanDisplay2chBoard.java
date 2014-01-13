package functional;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Iterator;

import nibura.logic.Board;
import nibura.logic.BoardGroup;
import nibura.logic.BoardList;
import nibura.logic.BoardListDownloader.MenuDownloadException;
import nibura.logic.BoardListDownloader.UnknownMenuAccessTypeException;
import nibura.logic.BoardListElement;
import nibura.logic.FileBoardLink;
import nibura.logic.InvalidSuiteTypeException;
import nibura.logic.NichBoardListFetcher;
import nibura.logic.ParsingErrorException;
import nibura.logic.ThreadLink;
import nibura.logic.ThreadList;
import nibura.logic.InvalidBoardException;
import nibura.logic.BoardLink;
import nibura.logic.PostListCreator;
import nibura.logic.BoardListElement.SuiteType;

import org.apache.commons.lang3.ArrayUtils;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.Assert;

public class CanDisplay2chBoard {
	private Board board = null;
	private BoardLink boardLink = null;
	private ThreadList postList = null;
	
	/*
	 * This test confirms that the parser should reject links that lack proper post links.
	 * Currently checks on the 2ch Suite. 2ch Suite expects the full post list to be at: "boardURL/subback.html"
	 */
	@Test(expected=InvalidBoardException.class)
	public void shouldFailCorrectly404Board() throws MalformedURLException, InvalidSuiteTypeException, ParsingErrorException, InvalidBoardException, FileNotFoundException {
		// Setup
		boardLink = new BoardLink("2NN+", "http://newsnavi.2ch.net/",SuiteType.NICH_SUITE);
		
		// Exercise & Test
		board = new Board(boardLink);
	}
	
	/*
	 * This test confirms that the parser is working correctly and is parsing content into proper logic objects
	 * This test doesn't check Last Update timestamps as the main code does not allow for easy arbitrary changes
	 *    to specific posts.
	 * This tests against a simplified prior-saved post list chunk.
	 */
	@Test
	public void shouldParseBoardList() throws FileNotFoundException, URISyntaxException, MalformedURLException, InvalidSuiteTypeException, ParsingErrorException, InvalidBoardException {
		// Setup		
		URI fileURI = TestResources.NICH_LIVE_BOARD_HTML.getURI();
		FileBoardLink boardLink = new FileBoardLink("2NN+", "http://newsnavi.2ch.net/", SuiteType.NICH_SUITE, new File(fileURI));

		// Build expected post list from pre-saved CSV file
		PostListCreator creator = new PostListCreator(TestResources.NICH_LIVE_BOARD_EXPECTED.getURI(), boardLink);
		ThreadList expectedPostList = creator.createPostListFromCSV();
		
		// Exercise
		board = new Board(boardLink);
		
		// Test
		Iterator<ThreadLink> expectedListIterator = expectedPostList.iterator();
		Iterator<ThreadLink> postListIterator = board.iterator();
		
		// Iterate through posts checking content is the same.
		boolean shouldBeTrue = true;
		while(expectedListIterator.hasNext() && postListIterator.hasNext() && shouldBeTrue) {
			ThreadLink expected = expectedListIterator.next();
			ThreadLink post = postListIterator.next();
			
			shouldBeTrue = expected.isSameThreadLink(post);
		}
		
		Assert.assertTrue(shouldBeTrue);
	}
	
	/*
	 * A full test run on all boards in 2ch Board list tree
	 * This test is a live test to confirm that the parser can handle processing of 2ch boards.
	 */
	@Ignore @Test
	public void shouldHandleAll2chBoards() throws UnknownMenuAccessTypeException, MenuDownloadException, ParsingErrorException, URISyntaxException, IOException, InvalidSuiteTypeException, InvalidBoardException {
		// Setup
		NichBoardListFetcher fetcher = new NichBoardListFetcher();
		BoardList fullBoardList = fetcher.getBoardList();
		
		// Exercise
		// Retrieve all board links
		ArrayList<BoardLink> links = new ArrayList<BoardLink>();
		for(BoardListElement element : fullBoardList) {
			links.addAll(getBoardLinks(element));
		}
		
		// Attempt parsing for post list on each board
		int processed = 0;
		int success = 0;
		int failed = 0;
		for(BoardLink boardLink : links) {
			try {
				new Board(boardLink);
			}
			catch(InvalidBoardException | ParsingErrorException e) {
				failed++;
				processed++;
				System.out.println("Skipped: " + boardLink.getName());
				continue;
			}
			success++;
			processed++;
		}
		
		// Test
		System.out.printf("OK/Fail/Total: %d/%d/%d", success, failed, processed);
		Assert.assertEquals(processed, links.size());
		
	}
	
	/*
	 * Used in the shouldHandleAll2chBoards test.
	 * This function retrieves all the board links so that they can be loaded
	 */
	private ArrayList<BoardLink> getBoardLinks(BoardListElement ele) {
		ArrayList<BoardLink> links = new ArrayList<BoardLink>();
		if(ele instanceof BoardLink) {
			links.add((BoardLink)ele);
		}
		else if(ele instanceof BoardGroup) {
			BoardGroup group = (BoardGroup)ele;
			// Make recursive calls collecting all links and adding them to the running array
			for(BoardListElement element : group) {
					links.addAll(getBoardLinks(element));
			}
		}
		
		return links;
	}
}
