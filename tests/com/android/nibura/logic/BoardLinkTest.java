package com.android.nibura.logic;

import static org.junit.Assert.*;

import java.net.MalformedURLException;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

public class BoardLinkTest {
	private final String BOARDNAME = "TEST_BOARD";
	private final String BOARD_STR_URL = "http://2ch.BOARDTEST.jp/";

	private BoardLink testBoardLink = null;
	
	@Before
	public void setUp() throws Exception {
		testBoardLink = null;
	}

	@Test
	public void testGetName() throws MalformedURLException {
		//=== Setup
		testBoardLink = new BoardLink(BOARDNAME,BOARD_STR_URL);
		
		//=== Exercise
		String testName = testBoardLink.getName();
		
		//=== Test
		Assert.assertEquals(BOARDNAME, testName);		
	}

	@Test
	public void testGetLink() throws MalformedURLException {
		//=== Setup
		testBoardLink = new BoardLink(BOARDNAME,BOARD_STR_URL);
		
		//=== Exercise
		String testURL = testBoardLink.getLink();
		
		//=== Test
		Assert.assertEquals(BOARD_STR_URL, testURL);	
	}

	@Test
	public void testToString() throws MalformedURLException {
		//=== Setup
		testBoardLink = new BoardLink(BOARDNAME, BOARD_STR_URL);
		String expectedString = BOARDNAME + " <" + BOARD_STR_URL + ">\n";
		
		//=== Exercise
		String testString = testBoardLink.toString();
		
		//=== Test
		Assert.assertEquals(expectedString, testString);
	}

}
