package functional;

import static org.junit.Assert.*;

import java.io.File;

import org.junit.Before;
import org.junit.Test;

import com.android.nibura.html.BoardListHTMLBuilder;
import com.android.nibura.logic.BoardList;
import com.android.nibura.logic.NichBoardListFetcher;

public class CanDisplayProperBoardListHtmL {
	private BoardListHTMLBuilder htmlBuilder = null;
	
	@Before
	public void setUp() throws Exception {
		File file_in = new File("tests/NichBoardListFetcher_TESTHTML.html");
		NichBoardListFetcher fetcher = new NichBoardListFetcher(file_in);
		BoardList boardList = fetcher.getBoardList();
		
		htmlBuilder = new BoardListHTMLBuilder(boardList);
	}

	@Test
	public void shouldGiveProperBoardListHTML() {
		// Setup
		String expectedString = 
		fail("Not yet implemented");
	}

}
