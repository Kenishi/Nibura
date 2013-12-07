package com.android.nibura.logic;

import static org.junit.Assert.*;

import java.io.File;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.android.nibura.logic.BoardListDownloader.MenuDownloadException;
import com.android.nibura.logic.BoardListDownloader.UnknownMenuAccessTypeException;

public class NichBoardListFetcherTest {
	private NichBoardListFetcher boardFetcher = null;
	
	@Before
	public void setUp() throws Exception {
		File boardListFile_in = new File("tests/NichBoardListFetcher_TESTHTML.html");
		boardFetcher = new NichBoardListFetcher(boardListFile_in);
	}

	@Test(timeout=3000)
	public void testGetBoardList() {
		//=== Setup
		BoardList boardList = null;
		try {
			boardList = boardFetcher.getBoardList();
		} catch (UnknownMenuAccessTypeException e) {
			fail(e.getMessage());
		} catch (MenuDownloadException e) {
			fail(e.getMessage());
		} catch (ParsingErrorException e) {
			fail(e.getMessage());
		}
		
		//=== Exercise
		String boardString = boardList.toString();
		
		String expectedString = "" +
				"**GROUP1\n" +
				"BOARD1 <http://board1.com/>\n" +
				"BOARD2 <http://board2.com/>\n" +
				"BOARD3 <http://board3.com/>\n" +
				"**GROUP2\n" +
				"CATS1 <http://cats1.com/>\n" +
				"CATS2 <http://cats2.com/>\n";
		
		//=== Test
		Assert.assertEquals(expectedString, boardString);		
	}

}
