package com.android.nibura.logic;

import static org.junit.Assert.*;

import java.io.File;

import org.junit.Before;
import org.junit.Test;

public class NichBoardListFetcherTest {
	private NichBoardListFetcher boardFetcher = null;
	
	@Before
	public void setUp() throws Exception {
		File boardListFile_in = new File("tests/2CH_TEST_MENU.html");
		boardFetcher = new NichBoardListFetcher(boardListFile_in);
	}

	@Test
	public void testGetBoardList() {
		
		
	}

}
