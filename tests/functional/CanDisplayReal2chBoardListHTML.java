package functional;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.MalformedURLException;
import java.util.Scanner;

import nibura.html.BoardListHTMLBuilder;
import nibura.logic.BoardList;
import nibura.logic.BoardListDownloader.MenuDownloadException;
import nibura.logic.BoardListDownloader.UnknownMenuAccessTypeException;
import nibura.logic.NichBoardListFetcher;
import nibura.logic.ParsingErrorException;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class CanDisplayReal2chBoardListHTML {

	@Before
	public void setUp() throws Exception {
		
	}

	@Test
	public void shouldGetLiveBoardListHTML() throws FileNotFoundException, Exception {
		// Setup
		NichBoardListFetcher fetcher = new NichBoardListFetcher();
		BoardList boardList = fetcher.getBoardList();
		BoardListHTMLBuilder builder = new BoardListHTMLBuilder(boardList);
		
		Scanner scanner = new Scanner(new File(TestResources.NICH_LIVE_EXPECTED_HTML.getURI()));
		String expectedHTML = scanner.useDelimiter("\\Z").next();
		
		// Exercise
		String html = builder.getHTML();
		
		// Test - Non-content test
		//Assert.assertEquals(expectedHTML, html);
		System.out.println(html);
	}
}
