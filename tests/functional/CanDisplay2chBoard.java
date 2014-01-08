package functional;

import static org.junit.Assert.*;

import java.net.MalformedURLException;

import junit.framework.Assert;
import nibura.logic.Board;
import nibura.logic.PostList;
import nibura.logic.Board.InvalidBoardException;
import nibura.logic.BoardLink;
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
	public void shouldFailCorrectlyNonBoard() throws MalformedURLException {
		// Setup
		boardLink = new BoardLink("2NN+", "http://newsnavi.2ch.net/",SuiteType.NICH_SUITE);
		
		// Exercise & Test
		board = new Board(boardLink);
	}
	
	@Test
	public void shouldParseBoardList() {
		// Setup
		// Exercise
		// Test
	}

	@Test
	public void shouldHandleAll2chBoards() {
		
	}
}
