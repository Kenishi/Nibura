package nibura.logic;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Date;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import nibura.logic.BoardListElement.SuiteType;

public class NichBoardFetcher extends AbstractBoardFetcher {
	ThreadList postList = new ThreadList();
	
	public NichBoardFetcher(BoardLink link) throws InvalidSuiteTypeException, ParsingErrorException, InvalidBoardException, MalformedURLException, FileNotFoundException {
		if(!(link instanceof FileBoardLink)) {
			
			// Confirm suite is as expected
			if(link.getSuiteType() != SuiteType.NICH_SUITE)
				throw new InvalidSuiteTypeException("Board Fetcher expected NICH_SUITE, but got "
								+ link.getSuiteType().toString() + "instead.");
			
			// Get HTML for board
			String html = retrieveHTML(link);
			
			// Begin parsing
			postList = parseHTML(html, link.getLink());
		}
		else { // File method for unit testing
			// Confirm suite is as expected
			if(link.getSuiteType() != SuiteType.NICH_SUITE)
				throw new InvalidSuiteTypeException("Board Fetcher expected NICH_SUITE, but got "
								+ link.getSuiteType().toString() + "instead.");
			//Get HTML from File
			FileBoardLink fileLink = (FileBoardLink)link;
			Scanner scanner = new Scanner(fileLink.getFile());
			String html = scanner.useDelimiter("\\Z").next();
			scanner.close();
			
			// Begin parsing
			postList = parseHTML(html,fileLink.getLink());
		}
	
	}
	
	protected NichBoardFetcher() {}
	
	public ThreadList getThreadList() {
		return postList;
	}
	
	private String retrieveHTML(BoardLink link) throws ParsingErrorException, InvalidBoardException {
		String content = "";
		try {
			String linkStr = link.getLink() + "subback.html"; // Get the larger backlog page
			content = BoardDownloader.getContentByURL(linkStr);
		} catch (IOException e) {
			throw new ParsingErrorException("Failed to download Board Content.\n" 
												+ "URL:" + link.getLink());
		}
		return content;
	}

	/**
	 * Parse the HTML into a PostList.
	 * @param html The HTML to parse
	 * @param boardURL The board URL 
	 * @return Return PostList of parsed board
	 * @throws ParsingErrorException 
	 * @throws MalformedURLException 
	 */
	private ThreadList parseHTML(String html, String boardURL) throws ParsingErrorException, MalformedURLException {
		ThreadList list = new ThreadList();
		Document doc = Jsoup.parse(html);
		
		Elements elements = doc.select("#trad > a");
		for(Element ele : elements) {
			String postURL = "";
			// Retrieve Post URL
			postURL = getPostURL(boardURL, ele);
			
			// Parse string
			String eleText = ele.text();
			String regex = "(?is)(?<ord>[\\d]+?): (?<title>(.*))(?:.*)(?:\\((?<count>\\d+)\\))";
			Pattern pattern = Pattern.compile(regex);
			Matcher matcher = pattern.matcher(eleText);
			matcher.find();
			String title, countStr = "";
			int count = 0;
			try {
				title = matcher.group("title");
				countStr = matcher.group("count");
				count = Integer.parseInt(countStr);
			}
			catch(IllegalStateException e) {
				System.out.println("Match not found: " + eleText);
				throw new ParsingErrorException("Match not found while parsing: " + boardURL, eleText);
			}
				
			ThreadLink post = new ThreadLink(title,postURL,count,SuiteType.NICH_SUITE,new Date());
			list.addThreadLink(post);
		}
		return list;
	}
	
	private String getPostURL(String boardURL, Element ele) throws ParsingErrorException {
		String url = null;
		if(ele.attr("href") != "") {
			url = boardURL + ele.attr("href");
		}
		else {
			throw new ParsingErrorException("Error parsing link html", ele.toString());
		}
		return url;
	}
}
