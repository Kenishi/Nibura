package nibura.logic;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.UUID;

import nibura.logic.BoardGroup;
import nibura.logic.BoardLink;
import nibura.logic.BoardListElement;
import nibura.logic.BoardListElement.SuiteType;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class BoardGroupTest {
	final private String TEST_GROUP_NAME = "Test!#$縺昴ｌ縺�ｈ縺ｭ";
	
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

	/**
	 * Tests the addition of the group name in the toString output.
	 * Does NOT test whether contained elements supply proper strings.
	 * See specific element test for that.
	 */
	@Test
	public void testBoardGroups_ToStringPart() {
		// Setup
		testGroup = new BoardGroup(TEST_GROUP_NAME);
		
		// Exercise
		String testString = testGroup.toString();
		
		// Test
		Assert.assertEquals("**" + TEST_GROUP_NAME + "\n", testString);
	}

	/**
	 * Tests the method for adding an array of BoardListElements
	 * @throws MalformedURLException
	 */
	@Test
	public void testAddElements() throws MalformedURLException {
		//==== Setup
		testGroup = new BoardGroup(TEST_GROUP_NAME);
		ArrayList<BoardLink> boardLinks = new ArrayList<BoardLink>();
		
		for(int boardCount=0; boardCount<=4; boardCount++) {
			BoardLink testBoard = new BoardLink("Test Board " + 1, "http://2ch.TESTBOARD" + 1 + ".jp/", SuiteType.NICH_SUITE);
			boardLinks.add(testBoard);
		}
		
		// Create test link and add it to array list
		String testName = "TEST_BOARD_LINK";
		String testURL = "http://2ch.TESTBOARDURL.jp/";
		BoardLink testLink = new BoardLink(testName, testURL, SuiteType.NICH_SUITE);
		boardLinks.add(testLink);
		
		int addedElementsCounter = boardLinks.size();
		
		//==== Exercise
		// Get a concrete array
		BoardLink boardArrayLinks[] = new BoardLink[0];
		boardArrayLinks = boardLinks.toArray(boardArrayLinks);

		testGroup.addElements(boardArrayLinks);
	
		//==== Test
		BoardListElement element = testGroup.debug_getElement(addedElementsCounter-1);
		Assert.assertEquals(testLink, element);
	}

	/**
	 * Tests the insert method
	 * @throws MalformedURLException
	 */
	@Test
	public void testAddElement_Insert() throws MalformedURLException {
		// Setup
		testGroup = new BoardGroup(TEST_GROUP_NAME);

		for(int boardCount=0; boardCount<=4; boardCount++) {
			BoardLink testBoard = new BoardLink("Test Board " + 1, "http://2ch.TESTBOARD" + 1 + ".jp/", SuiteType.NICH_SUITE);
			testGroup.addElement(testBoard);
		}
		
		String testName = "TEST_BOARD_LINK";
		String testURL = "http://2ch.TESTBOARDURL.jp/";
		BoardLink boardLink = new BoardLink(testName, testURL, SuiteType.NICH_SUITE);
		
		//=== Exercise
		testGroup.addElement(boardLink, 2);
		
		//=== Test
		BoardListElement testElement = testGroup.debug_getElement(2);
		Assert.assertEquals(boardLink, testElement);
	}

	/**
	 * Tests generic addElement method
	 * @throws MalformedURLException
	 */
	@Test
	public void testAddElement() throws MalformedURLException {
		// === Setup
		testGroup = new BoardGroup(TEST_GROUP_NAME);
		BoardLink boardLink = new BoardLink("Test Board 1", "http://2ch.TESTBOARD1.jp/", SuiteType.NICH_SUITE);
				
		// === Exercise
		testGroup.addElement(boardLink);
		BoardListElement testElement = testGroup.debug_getElement(0);
		
		// === Test
		Assert.assertEquals(boardLink, testElement);		
	}
	
	@Test
	public void testGetElementByID() throws MalformedURLException {
		// Setup
		testGroup = new BoardGroup(TEST_GROUP_NAME);
		BoardLink boardLink = new BoardLink("Test Board 1", "http://2ch.TESTBOARD1.jp/", SuiteType.NICH_SUITE);
		String idStr = boardLink.getId();
		testGroup.addElement(boardLink);
		
		// Exercise
		BoardListElement element = testGroup.getElementByID(UUID.fromString(idStr));
		
		// Test
		Assert.assertSame(boardLink, element);
	}

}
