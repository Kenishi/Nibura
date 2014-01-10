package functional;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Scanner;

import junit.framework.Assert;
import nibura.logic.Board;
import nibura.logic.FileBoardLink;
import nibura.logic.InvalidSuiteTypeException;
import nibura.logic.ParsingErrorException;
import nibura.logic.PostList;
import nibura.logic.InvalidBoardException;
import nibura.logic.BoardLink;
import nibura.logic.PostListCreator;
import nibura.logic.BoardListElement.SuiteType;

import org.junit.Before;
import org.junit.Test;

public class CanDisplay2chBoard {
	private Board board = null;
	private BoardLink boardLink = null;
	private PostList postList = null;
	
	@Before
	public void setUp() throws Exception {
	}

	@Test(expected=InvalidBoardException.class)
	public void shouldFailCorrectly404Board() throws MalformedURLException, InvalidSuiteTypeException, ParsingErrorException, InvalidBoardException {
		// Setup
		boardLink = new BoardLink("2NN+", "http://newsnavi.2ch.net/",SuiteType.NICH_SUITE);
		
		// Exercise & Test
		board = new Board(boardLink);
	}
	
	@Test
	public void shouldParseBoardList() throws FileNotFoundException, URISyntaxException, MalformedURLException {
		// Setup		
		URI fileURI = TestResources.NICH_LIVE_BOARD_HTML.getURI();
		FileBoardLink boardLink = new FileBoardLink("2NN+", "http://newsnavi.2ch.net/", SuiteType.NICH_SUITE, new File(fileURI));

		// Build expected post list from pre-saved CSV file
		PostListCreator creator = new PostListCreator(TestResources.NICH_LIVE_BOARD_EXPECTED.getURI(), boardLink);
		PostList expectedPostList = creator.createPostListFromCSV();
		
		// Exercise
		board = new Board(boardLink);
		
		// Test
		for(
	}

	@Test
	public void shouldHandleAll2chBoards() {
		
	}
}
