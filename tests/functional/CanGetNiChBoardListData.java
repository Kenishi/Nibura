package functional;
import java.io.File;
import java.util.Arrays;
import java.util.Iterator;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.android.nibura.logic.BoardList;
import com.android.nibura.logic.BoardListDownloader.MenuDownloadException;
import com.android.nibura.logic.BoardListDownloader.UnknownMenuAccessTypeException;
import com.android.nibura.logic.BoardListElement;
import com.android.nibura.logic.BoardListFetcher;
import com.android.nibura.logic.NichBoardListFetcher;
import com.android.nibura.logic.ParsingErrorException;


public class CanGetNiChBoardListData {
	private BoardListFetcher fetcher = null; 
	
	@Before
	public void setUp() throws Exception {
		File testBoardList = new File("tests/2CH_TEST_MENU.html");
		fetcher = new NichBoardListFetcher(testBoardList);
	}

	@Test
	public void shouldGiveBoardList() {
		BoardList boardList = null;
		try {
			boardList = fetcher.getBoardList();
		} catch (UnknownMenuAccessTypeException e) {
			Assert.fail(e.getMessage());
		} catch (MenuDownloadException e) {
			Assert.fail(e.getMessage());
		} catch (ParsingErrorException e) {
			Assert.fail(e.getMessage());
		}
		
		Assert.assertSame(boardList.getClass(), BoardList.class);
	}
	
	@Test(timeout=3000)
	public void shouldHaveBoards_toStringTest() {
		// Setup
		String expectedBoardListString = "";
		expectedBoardListString += "**地震\n"
				+ "地震headline <http://headline.2ch.net/bbynamazu/>\n"
				+ "地震速報 <http://anago.2ch.net/namazuplus/>\n"
				+ "臨時地震 <http://hayabusa.2ch.net/eq/>\n"
				+ "**おすすめ\n"
				+ "プロ野球 <http://uni.2ch.net/base/>\n"
				+ "家電製品 <http://awabi.2ch.net/kaden/>\n"
				+ "**特別企画\n"
				+ "2ch検索 <http://find.2ch.net/>\n"
				+ "be.2ch.net <http://be.2ch.net/>\n"
				+ "**be\n"
				+ "面白ネタnews <http://kohada.2ch.net/be/>\n"
				+ "なんでも質問 <http://ikura.2ch.net/nandemo/>\n";
		
		// Exercise
		BoardList boardList = null;
		try {
			boardList = fetcher.getBoardList();
		} catch (UnknownMenuAccessTypeException e) {
			Assert.fail(e.getMessage());
		} catch (MenuDownloadException e) {
			Assert.fail(e.getMessage());
		} catch (ParsingErrorException e) {
			Assert.fail(e.getMessage());
		}
		String boardListStr = boardList.toString(); 
		
		// Test
		Assert.assertEquals(expectedBoardListString, boardListStr);
	}
	
	@Test
	public void shouldHaveNonBoardsRemoved() {
		BoardList boardList = null;
		try {
			boardList = fetcher.getBoardList();
		} catch (UnknownMenuAccessTypeException e) {
			Assert.fail(e.getMessage());
		} catch (MenuDownloadException e) {
			Assert.fail(e.getMessage());
		} catch (ParsingErrorException e) {
			Assert.fail(e.getMessage());
		}
		String removedGroupNames[] = {"チャット", "運営案内", "ツール類", "BBSPINK", "まちＢＢＳ", "他のサイト"};
		
		// Verify that none of the group names exist in the board list
		Iterator<BoardListElement> boardElements = boardList.iterator();
		while(boardElements.hasNext()) {
			BoardListElement element = boardElements.next();
			String elementName = element.getName();
			
			boolean hasName = Arrays.asList(removedGroupNames).contains(elementName);
			
			Assert.assertFalse(hasName);
		}
	}
	
	@Test
	public void shouldGiveProperHTMLForList() {
		BoardList boardList = null;
		try {
			boardList = fetcher.getBoardList();
		} catch (UnknownMenuAccessTypeException | MenuDownloadException
				| ParsingErrorException e) {
			Assert.fail(e.getMessage());
		}
		
		
	}
		

}
