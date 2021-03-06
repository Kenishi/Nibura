package nibura.logic;

import java.net.MalformedURLException;

import nibura.logic.BoardLink;
import nibura.logic.BoardListElement.SuiteType;

import org.junit.Assert;
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
		testBoardLink = new BoardLink(BOARDNAME,BOARD_STR_URL, SuiteType.NICH_SUITE);
		
		//=== Exercise
		String testName = testBoardLink.getName();
		
		//=== Test
		Assert.assertEquals(BOARDNAME, testName);		
	}

	@Test
	public void testGetLink() throws MalformedURLException {
		//=== Setup
		testBoardLink = new BoardLink(BOARDNAME,BOARD_STR_URL, SuiteType.NICH_SUITE);
		
		//=== Exercise
		String testURL = testBoardLink.getLink();
		
		//=== Test
		Assert.assertEquals(BOARD_STR_URL, testURL);	
	}

	@Test
	public void testToString() throws MalformedURLException {
		//=== Setup
		testBoardLink = new BoardLink(BOARDNAME, BOARD_STR_URL, SuiteType.NICH_SUITE);
		String expectedString = BOARDNAME + " (ID: $ID$)" + " <" + BOARD_STR_URL + ">\n";
		
		//=== Exercise
		String testString = testBoardLink.toString();
		String id = testBoardLink.getId();
		expectedString = expectedString.replace("$ID$", id);
		
		//=== Test
		Assert.assertEquals(expectedString, testString);
	}

}
