package functional;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;

import nibura.html.BoardListHTMLBuilder;
import nibura.logic.BoardList;
import nibura.logic.NichBoardListFetcher;
import nibura.logic.RUNTIME_STATUS;
import nibura.logic.RUNTIME_STATUS.STATUS;

import org.junit.Before;
import org.junit.Test;

public class CanDisplay2chBoardListHtml {
	private BoardListHTMLBuilder htmlBuilder = null;
	
	@Before
	public void setUp() throws Exception {
		RUNTIME_STATUS.setStatus(STATUS.DEBUG);
		
		File file_in = new File(TestResources.TWOCH_LIST_HTML_FILE.getURI());
		NichBoardListFetcher fetcher = new NichBoardListFetcher(file_in);
		BoardList boardList = fetcher.getBoardList();
		
		htmlBuilder = new BoardListHTMLBuilder(boardList);
	}

	@Test
	public void shouldGiveProperHTMLContent() throws FileNotFoundException, Exception {
		// Setup
		String expectedString = 
				  "<div class=\"group\">"
				+ 	"<div class=\"group-header\" style=\"padding:5px;\">"
				+ 		"<span class=\"open-close-icon\"></span>地震"
				+ 	"</div>\n"
				+ 	"<div class=\"group-content\" style=\"margin-left:10px;\">\n"
				+		"<A HREF=\"http://headline.2ch.net/bbynamazu/\">地震headline</A><br/>\n"
				+		"<A HREF=\"http://anago.2ch.net/namazuplus/\">地震速報</A><br/>\n"
				+		"<A HREF=\"http://hayabusa.2ch.net/eq/\">臨時地震</A><br/>\n"
				+ 	"</div><!--Close Group Content-->\n"
				+ "</div><!--Close Group-->\n"
				+ "<div class=\"group\">"
				+ 	"<div class=\"group-header\" style=\"padding:5px;\">"
				+ 		"<span class=\"open-close-icon\"></span>おすすめ"
				+ 	"</div>\n"
				+ 	"<div class=\"group-content\" style=\"margin-left:10px;\">\n"
				+		"<A HREF=\"http://uni.2ch.net/base/\">プロ野球</A><br/>\n"
				+		"<A HREF=\"http://awabi.2ch.net/kaden/\">家電製品</A><br/>\n"
				+ 	"</div><!--Close Group Content-->\n"
				+ "</div><!--Close Group-->\n"
				+ "<div class=\"group\">"
				+ 	"<div class=\"group-header\" style=\"padding:5px;\">"
				+ 		"<span class=\"open-close-icon\"></span>特別企画"
				+ 	"</div>\n"
				+ 	"<div class=\"group-content\" style=\"margin-left:10px;\">\n"
				+		"<A HREF=\"http://find.2ch.net/\">2ch検索</A><br/>\n"
				+		"<A HREF=\"http://be.2ch.net/\">be.2ch.net</A><br/>\n"
				+ 	"</div><!--Close Group Content-->\n"
				+ "</div><!--Close Group-->\n"
				+ "<div class=\"group\">"
				+ 	"<div class=\"group-header\" style=\"padding:5px;\">"
				+ 		"<span class=\"open-close-icon\"></span>be"
				+ 	"</div>\n"
				+ 	"<div class=\"group-content\" style=\"margin-left:10px;\">\n"
				+		"<A HREF=\"http://kohada.2ch.net/be/\">面白ネタnews</A><br/>\n"
				+		"<A HREF=\"http://ikura.2ch.net/nandemo/\">なんでも質問</A><br/>\n"
				+ 	"</div><!--Close Group Content-->\n"
				+ "</div><!--Close Group-->\n";
		// Exercise
		String htmlString = htmlBuilder.getHTML();
		
		// Test
		// Test using contains() - DOES NOT VERIFY CORRECT POSITION
		if(!htmlString.toLowerCase().contains(expectedString.toLowerCase()))
			fail("HTML Content does not match or was not found.");
	}
	
	@Test
	public void testOutput() throws Exception {
		String html = htmlBuilder.getHTML();
		
		File file_out = new File("test2ch_output.html");
		FileWriter writer = new FileWriter(file_out);
		writer.write(html);
		
		writer.close();		
	}

}
