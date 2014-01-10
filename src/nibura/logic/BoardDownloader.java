/**
 * BoardDownloader.java
 * 
 * This class serves wraps all the functionality needed for retrieving the HTML
 * content from a URL.
 * 
 * It should be noted that the BoardDownloader is Suite neutral. All processing needed
 * to figure out where to look for a Board's post list should be done inside the Suite's
 * associated BoardFetcher. From there the fetcher will provide the URL that the
 * BoardDownloader will use. So in practice, the BoardDownloader should only be called
 * from a Fetcher.
 * 
 * The downloader makes use of the Apache HTTP Client as the means for retrieving
 * the content. This is used over other internal Java methods as the Apache client
 * more thoroughly implements the HTTP protocol and allows for more information retrieval.
 *  
 */

package nibura.logic;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

import org.apache.http.HttpEntity;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

public class BoardDownloader {

	/**
	 * Retrieve the content of the url.
	 * @param url String containing a URL
	 * @return String containing the HTML for the page. 
	 * @throws ClientProtocolException Thrown by the HTTP client. See Apache Http Client documentation for more info.
	 * @throws IOException Thrown for errors encountered during reading the content.
	 * @throws InvalidBoardException Thrown when errors are encountered when trying to retrieve the URL. ex: 404 NOT FOUND
	 */
	protected static String getContentByURL(String url) throws ClientProtocolException, IOException, InvalidBoardException {
		CloseableHttpClient client = HttpClients.createDefault();
		HttpGet method = new HttpGet(url);
		CloseableHttpResponse response = client.execute(method);
		
		// Check for 404 NOT FOUND
		StatusLine status = response.getStatusLine();
		if(status.getStatusCode() == 404) {
			throw new InvalidBoardException("404 BOARD URL NOT FOUND (" + url + ")");
		}
		
		String content = "";
		try {
			HttpEntity entity =  response.getEntity();
			BufferedReader reader = new BufferedReader(new InputStreamReader(entity.getContent(),Charset.forName("SJIS")));
			String line = "";
			while((line = reader.readLine()) != null ) {
				content += line;
			}
		}
		finally {
			response.close();
		}
		
		return content;
	}
	
}
