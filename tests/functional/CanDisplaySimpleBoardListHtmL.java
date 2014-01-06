package functional;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileNotFoundException;

import nibura.html.BoardListHTMLBuilder;
import nibura.logic.BoardList;
import nibura.logic.NichBoardListFetcher;

import org.junit.Before;
import org.junit.Test;

public class CanDisplaySimpleBoardListHtmL {
	private BoardListHTMLBuilder htmlBuilder = null;
	
	@Before
	public void setUp() throws Exception {
		File file_in = new File(TestResources.SIMPLE_BOARDLIST_HTML_FILE.getURI());
		NichBoardListFetcher fetcher = new NichBoardListFetcher(file_in);
		BoardList boardList = fetcher.getBoardList();
		
		htmlBuilder = new BoardListHTMLBuilder(boardList);
	}

	@Test
	public void shouldContainProperHTMLHeader() throws FileNotFoundException, Exception {
		// Setup
		String expectedString = "<!DOCTYPE html>\r\n"
				+ "<html>\r\n"
				+ "<head>\r\n"
				+ "<meta charset=\"UTF-8\">\r\n"
				+ "<script src=\"src/nibura/html/jquery-2.0.3.min.js\" type=\"text/javascript\"/></script>\r\n"
				+ "<script src=\"src/nibura/html/BoardList.js\" type=\"text/javascript\"/></script>\r\n"
				+ "</head>\r\n"
				+ "<body>\r\n"
				+ "<div id=\"container\">\r\n";
		
		
		// Exercise
		String htmlString = htmlBuilder.getHTML();
		
		// Test using contains() - DOES NOT VERIFY CORRECT POSITION
		if(!htmlString.toLowerCase().contains(expectedString.toLowerCase()))
			fail("HTML Header does not match or was not found.");
	}
	
	@Test
	public void shouldGiveProperHTMLContent() throws FileNotFoundException, Exception {
		// Setup
		String expectedString = 
				  "<div class=\"group\">"
				+ 	"<div class=\"group-header\" style=\"padding:5px;\">"
				+ 		"<span class=\"open-close-icon\"></span>GROUP1"
				+ 	"</div>\n"
				+ 	"<div class=\"group-content\" style=\"margin-left:10px;\">\n"
				+ 		"<a href=\"http://board1.com/\">BOARD1</a><br/>\n"
				+ 		"<a href=\"http://board2.com/\">BOARD2</a><br/>\n"
				+ 		"<a href=\"http://board3.com/\">BOARD3</a><br/>\n"
				+ 	"</div><!--Close Group Content-->\n"
				+ "</div><!--Close Group-->\n"
				+ "<div class=\"group\">"
				+ 	"<div class=\"group-header\" style=\"padding:5px;\">"
				+ 		"<span class=\"open-close-icon\"></span>GROUP2"
				+ 	"</div>\n"
				+ 	"<div class=\"group-content\" style=\"margin-left:10px;\">\n"
				+ 	"<a href=\"http://cats1.com/\">CATS1</a><br/>\n"
				+ 	"<a href=\"http://cats2.com/\">CATS2</a><br/>\n"
				+ 	"</div><!--Close Group Content-->\n"
				+ "</div><!--Close Group-->\n";
		
		// Exercise
		String htmlString = htmlBuilder.getHTML();
		
		// Test using contains() - DOES NOT VERIFY CORRECT POSITION
		if(!htmlString.toLowerCase().contains(expectedString.toLowerCase()))
			fail("HTML Content does not match or was not found.");
	}
	
	@Test
	public void shouldGiveProperHTMLFooter() throws FileNotFoundException, Exception {
		// Setup
		String expectedString = "</div>\r\n"
				+ "<script>\r\n"
				+ "panelinit();\r\n"
				+ "</script>\r\n"
				+ "</body>\r\n"
				+ "</html>";
		
		// Exercise 
		String htmlString = htmlBuilder.getHTML();
		
		// Test using contains() - DOES NOT VERIFY CORRECT POSITION
		if(!htmlString.toLowerCase().contains(expectedString.toLowerCase()))
			fail("HTML Footer does not match or was not found.");
	}

}
