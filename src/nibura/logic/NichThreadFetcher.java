package nibura.logic;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;

import org.apache.http.client.ClientProtocolException;

import nibura.logic.BoardListElement.SuiteType;

public class NichThreadFetcher extends AbstractThreadFetcher {
	private Thread thread = null;
	
	public NichThreadFetcher(ThreadLink link) throws InvalidSuiteTypeException {
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
		
		
		return null;
	}

	
}
