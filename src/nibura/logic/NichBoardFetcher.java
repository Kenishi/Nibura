package nibura.logic;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import nibura.logic.BoardListElement.SuiteType;

public class NichBoardFetcher extends AbstractBoardFetcher {
	PostList postList = new PostList();
	
	public NichBoardFetcher(BoardLink link) throws InvalidSuiteTypeException, ParsingErrorException, InvalidBoardException, MalformedURLException {
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
		else {
			// Confirm suite is as expected
			if(link.getSuiteType() != SuiteType.NICH_SUITE)
				throw new InvalidSuiteTypeException("Board Fetcher expected NICH_SUITE, but got "
								+ link.getSuiteType().toString() + "instead.");
			//Get HTML from File
			Scanner scanner = new Scanner()
		}
	
	}
	
	protected NichBoardFetcher() {}
	
	public PostList getPostList() {
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
	private PostList parseHTML(String html, String boardURL) throws ParsingErrorException, MalformedURLException {
		PostList list = new PostList();
		Document doc = Jsoup.parse(html);
		
		Elements elements = doc.select("#trad > a");
		for(Element ele : elements) {
			String postURL = "";
			// Retrieve Post URL
			postURL = getPostURL(boardURL, ele);
			
			// Parse string
			String eleText = ele.text();
			String regex = "(?is)(?<ord>[\\d]+?): (?<title>(.+))(?:\\((?<count>\\d+)\\))";
			Pattern pattern = Pattern.compile(regex);
			Matcher matcher = pattern.matcher(eleText);
			matcher.find();
			
			String title = matcher.group("title");
			String countStr = matcher.group("count");
			int count = Integer.parseInt(countStr);
			
			PostLink post = new PostLink(title,postURL,count,SuiteType.NICH_SUITE,new Date());
			list.addPostLink(post);
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
