package functional;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import nibura.html.BoardListHTMLBuilder;
import nibura.logic.BoardList;
import nibura.logic.NichBoardListFetcher;
import nibura.logic.RUNTIME_STATUS;
import nibura.logic.RUNTIME_STATUS.STATUS;

import org.junit.Before;
import org.junit.Test;

public class HTMLFileTest {
	private BoardListHTMLBuilder htmlBuilder = null;
	
	@Before
	public void setUp() throws Exception {
		File file_in = new File(TestResources.SIMPLE_BOARDLIST_HTML_FILE.getURI());
		NichBoardListFetcher fetcher = new NichBoardListFetcher(file_in);
		BoardList boardList = fetcher.getBoardList();
		
		htmlBuilder = new BoardListHTMLBuilder(boardList);
	}

	@Test
	public void shouldGiveProperFile() throws Exception {
		// Setup
		RUNTIME_STATUS.setStatus(STATUS.DEBUG);
		File fileOut = new File("testBoard_out.html");
		FileWriter writer = new FileWriter(fileOut);
		
		// Exercise
		String htmlText = htmlBuilder.getHTML();
		writer.write(htmlText);
		
		writer.close();
		
	}

}
