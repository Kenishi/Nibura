package functional;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import nibura.html.BoardListHTMLBuilder;
import nibura.logic.BoardList;
import nibura.logic.NichBoardListFetcher;

import org.junit.Before;
import org.junit.Test;

public class CanDisplayProperBoardListHtmL {
	private BoardListHTMLBuilder htmlBuilder = null;
	
	@Before
	public void setUp() throws Exception {
		File file_in = new File(TestResources.SIMPLE_BOARDLIST_HTML_FILE.getURI());
		NichBoardListFetcher fetcher = new NichBoardListFetcher(file_in);
		BoardList boardList = fetcher.getBoardList();
		
		htmlBuilder = new BoardListHTMLBuilder(boardList);
	}

	@Test
	public void shouldGiveProperHTMLHeaer() throws FileNotFoundException, Exception {
		// Setup
		String expectedString = "<html>\n"
				+ "<head>\n"
				+ "<script src=\"src/com/android/nibura/html/jquery-2.0.3.min.js\" />\n"
				+ "<script src=\"src/com/android/nibura/html/BoardList.js\" />\n"
				+ "<body>\n"
				+ "<div id=\"container\">\n";
		
		
		// Excercise
		String htmlString = htmlBuilder.getHTML();
		
		// Test
		Pattern regex = Pattern.compile(expectedString);
		Matcher match = regex.matcher(htmlString);
		if(!match.find())
			fail("HTML Header does not match or not found");
	}
	
	@Test
	public void shouldGiveProperHTMLContent() {
		fail("Not yet implemented");
	}
	
	@Test
	public void shouldGiveProperHTMLFooter() {
		fail("Not yet implemented");
	}

}
