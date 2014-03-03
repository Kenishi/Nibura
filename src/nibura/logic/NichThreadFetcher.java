package nibura.logic;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.client.ClientProtocolException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.parser.Tag;
import org.jsoup.select.Elements;

import nibura.logic.BoardListElement.SuiteType;

public class NichThreadFetcher extends AbstractThreadFetcher {
	private ArrayList<Post> postList = null;
	
	public NichThreadFetcher(ThreadLink link) throws InvalidSuiteTypeException, ParsingErrorException, PostParsingException {
		// Confirm Thread Link Suite is of correct type
		if(link.getSuiteType() != SuiteType.NICH_SUITE) {
			throw new InvalidSuiteTypeException("Thread Fetcher expected NICH_SUITE but got " + 
												link.getSuiteType().toString() + "instead.");
		}
		
		// Get HTML from the Thread
		String html = retrieveHTML(link);
		
		// Parse
		postList = parseHTML(html);
	}

	@Override
	public ArrayList<Post> getPosts() {
		return postList;
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
	private ArrayList<Post> parseHTML(String html) throws PostParsingException {
		Document doc = Jsoup.parse(html);
		Elements threadPosts = doc.select(".thread > *");
		
		ArrayList<Element> postElements = new ArrayList<Element>();
		ArrayList<Post> posts = new ArrayList<Post>();
		for(Element ele: threadPosts) {
			if(ele.tag() == Tag.valueOf("DT")) {
				try {
					posts.add(createPostFromDOM(postElements));
				}
				catch(PostParsingException e) {
					System.out.println(e.getMessage());
					continue;
				}
			}
		}
		
		return null;
	}
	
	private Post createPostFromDOM(ArrayList<Element> postElements) throws PostParsingException {
		// Parse post elements. DT=Header DD=PostText
		Hashtable<String,String> postHeader = null;
		String postText = null;
		for(Element ele : postElements) {
			if(ele.tag().equals(Tag.valueOf("DT")))
				postHeader = parsePostHeader(ele);
			else if(ele.tag().equals(Tag.valueOf("DD")))
				postText = parsePostText(ele);
			else {
				if(postHeader != null && postText != null) {
					System.err.println("Unknown post element encountered. Returning already parsed data.\n"
							+ "Tag: " + ele.tag().toString()
							+ "Data: " + ele.tag().toString());
				}
				else {
					throw new PostParsingException("Unknown post element encountered.\n"
							+ "Tag: " + ele.tag().toString()
							+ "Data: " + ele.toString());
				}
			}
		}
		
		Post post = buildPost(postHeader, postText);
		return post;
	}
	
	private Post buildPost(Hashtable<String,String> header, String postText) {
		return null;
	}
	
	private Hashtable<String,String> parsePostHeader(Element header) throws PostParsingException {
		if(header.childNodeSize() != 3) {
			throw new PostParsingException("Error parsing post header."
					+ " 3 elements expected but " + header.childNodeSize() + " found.");
		}
		
		// Setup Pattern & Matcher
		Pattern pattern = null;
		Matcher match = null;
		
		// 1st Node 
		
		return null;
	}
	
	private String parsePostText(Element text) {
		return null;
	}
	
	@SuppressWarnings("serial")
	public class PostParsingException extends Exception {
		public PostParsingException(String msg) {
			super(msg);
		}
	}
}
