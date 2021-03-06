package nibura.logic;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;

import nibura.logic.BoardList;
import nibura.logic.ParsingErrorException;
import nibura.logic.BoardListDownloader.MenuDownloadException;
import nibura.logic.BoardListDownloader.UnknownMenuAccessTypeException;

import org.apache.http.client.ClientProtocolException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import functional.TestResources;

public class NichBoardListFetcherTest {
	private NichBoardListFetcher boardFetcher = null;
	
	@Before
	public void setUp() throws Exception {
		File boardListFile_in = new File(TestResources.SIMPLE_BOARDLIST_HTML_FILE.getURI());
		boardFetcher = new NichBoardListFetcher(boardListFile_in);
	}

	@Test(timeout=3000)
	public void testGetBoardList() {
		//=== Setup
		BoardList boardList = null;
		try {
			boardList = boardFetcher.getBoardList();
		} catch(Exception e) {
			fail(e.getMessage());
		}
		
		//=== Exercise
		String boardString = boardList.toStringNoID();
		
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
