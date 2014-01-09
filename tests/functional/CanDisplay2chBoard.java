package functional;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.util.Scanner;

import junit.framework.Assert;
import nibura.logic.Board;
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
	public void shouldParseBoardList() throws FileNotFoundException, URISyntaxException {
		// Setup
		File boardFile = new File(TestResources.NICH_LIVE_BOARD_HTML.getURI());
		Scanner scanner = new Scanner(boardFile);
		String data = scanner.useDelimiter("\\Z").next();
		
		PostListCreator creator = new PostListCreator(TestResources.NICH_LIVE_BOARD_EXPECTED.getURI());
		
		// Exercise
		
		// Test
	}

	@Test
	public void shouldHandleAll2chBoards() {
		
	}
}
