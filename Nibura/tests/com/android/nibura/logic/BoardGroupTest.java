package com.android.nibura.logic;

import static org.junit.Assert.*;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Random;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.android.nibura.logic.BoardGroup;
import com.android.nibura.logic.BoardLink;

public class BoardGroupTest {
	final private String TEST_GROUP_NAME = "Test!#$それだよね";
	
	private BoardGroup testGroup = null;
	
	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testGetName() {
		// Setup
		testGroup = new BoardGroup(TEST_GROUP_NAME);
		
		// Exercise
		String groupName = testGroup.getName();
		
		// Test
		Assert.assertEquals(TEST_GROUP_NAME, groupName);;
	}

	@Test
	public void testGetLink() {
		// Setup 
		testGroup = new BoardGroup(TEST_GROUP_NAME);
		
		// Exercise
		String boardLink = testGroup.getLink();
		
		// Test
		Assert.assertEquals(BoardGroup.NO_BOARD_LINK, boardLink);
	}

	@Test
	public void testToString() {
		// Setup
		testGroup = new BoardGroup(TEST_GROUP_NAME);
		
		// Exercise
		String testString = testGroup.toString();
		
		// Test
		Assert.assertEquals("**" + TEST_GROUP_NAME + "\n", testString);
	}

	@Test
	public void testAddElementBoardListElement() throws MalformedURLException {
		// Setup
		testGroup = new BoardGroup(TEST_GROUP_NAME);
		
		Random rand = new Random();
		ArrayList<BoardLink> boardLinks = new ArrayList<BoardLink>();
		for(int counter=10; counter >= 0; counter--) {
			String boardName = Integer.toHexString(rand.nextInt(32));
			String boardURL = "http://2ch." + boardName + ".jp/";
			boardLinks.add(new BoardLink(boardName, boardURL));
		}
		
		// Exercise
		BoardLink boardArrayLinks[] = boardLinks.toArray(null);
		testGroup.addElements(boardArrayLinks);
		
		
	}

	@Test
	public void testAddElementBoardListElementInt() {
		fail("Not yet implemented");
	}

	@Test
	public void testAddElements() {
		fail("Not yet implemented");
	}

}
