package nibura.logic;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;

import org.apache.http.client.ClientProtocolException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.parser.Tag;
import org.jsoup.select.Elements;

import nibura.logic.BoardListElement.SuiteType;

public class NichThreadFetcher extends AbstractThreadFetcher {
	private Thread thread = null;
	
	public NichThreadFetcher(ThreadLink link) throws InvalidSuiteTypeException, ParsingErrorException {
		// Confirm Thread Link Suite is of correct type
		if(link.getSuiteType() != SuiteType.NICH_SUITE) {
			throw new InvalidSuiteTypeException("Thread Fetcher expected NICH_SUITE but got " + 
												link.getSuiteType().toString() + "instead.");
		}
		
		// Get HTML from the Thread
		String html = retrieveHTML(link);
		
		// Parse
		thread = parseHTML(html);
	}

	@Override
	public Thread getThread() {
		return thread;
	}

	// Private Methods 
	
	private String retrieveHTML(ThreadLink link) throws ParsingErrorException {
		String content = "";
		
		try {
			content = ThreadDownloader.getContentByURL(link.getURL());
		} catch (URISyntaxException | IOException
				| InvalidThreadException e) {
			throw new ParsingErrorException("Failed to download Thread content.\n"
											+ "URL:" + link.getURL().toString());
		}
		
		return content;
	}
	private Thread parseHTML(String html) {
		Document doc = Jsoup.parse(html);
		Elements threadPosts = doc.select(".thread > *");
		
		ArrayList<Elements> postElements = new ArrayList<Elements>();
		ArrayList<Post> posts = new ArrayList<Post>();
		for(Element ele: threadPosts) {
			if(ele.tag() == Tag.valueOf("DT")) {
				posts.add(createPostFromDOM(postElements));
			}
		}
		
		return null;
	}
	
	private Post createPostFromDOM(ArrayList<Elements> postElements) {
		
	}

	
}
