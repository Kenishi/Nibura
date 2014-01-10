/**
 * BoardListDownloader.java
 * 
 * This class serves as a wrapper for the functionality of retrieving the content of a 
 * board list, either from a web page or a file.
 * 
 * This class is suite neutral and therefore will not do any processing to determine
 * the correct location for where to find a board. The processing to figure out 
 * where to find the board list should be handled by the fetcher. Therefore, in practice
 * a fetcher class should be the only class attempting to call this.
 */

package nibura.logic;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.Scanner;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

public class BoardListDownloader {
	private File menu_file = null; // Saved file with menu HTML
	private URL menu_URL = null; // URL to get HTML from
	
	/**
	 * Constructor for getting a board list from locally saved HTML File.
	 * @param file File class with path to the HTML file
	 * @throws FileNotFoundException Thrown when the file can't be found
	 */
	protected BoardListDownloader(File file) throws FileNotFoundException {
		if( !file.exists() ) {
			throw new FileNotFoundException();
		}
		menu_file = file;
	}
	
	/**
	 * Constructor for getting a board list from a web site
	 * @param url A URL pointing to the board list HTML
	 */
	protected BoardListDownloader(URL url) {
		menu_URL = url;
	}
	
	/**
	 * Retrieve the HTML.
	 * @return Returns the board HTML in String.
	 * @throws UnknownMenuAccessTypeException Thrown when neither a File or URL has been specified. This shouldn't occur.
	 * @throws MenuDownloadException Thrown when there was an error encountered trying to get the board.
	 * @throws URISyntaxException Thrown if there was an error converting the URL to a general URI
	 * @throws IOException Thrown when there is trouble reading either the File or the URL data
	 */
	protected String getBoardMenuHTML() 
			throws UnknownMenuAccessTypeException, MenuDownloadException, URISyntaxException, IOException {
		if(menu_file != null) {
			return getContentByFile();
		}
		else if(menu_URL != null) {
			return getContentByURL();
		}
		else {
			throw new UnknownMenuAccessTypeException();
		}
	}
	
	
	private String getContentByURL() throws URISyntaxException, IOException {
		CloseableHttpClient client = HttpClients.createDefault();
		HttpGet httpget = new HttpGet(menu_URL.toURI());
		CloseableHttpResponse response = client.execute(httpget);
		String content = null;
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

	private String getContentByFile() throws MenuDownloadException {
		// Use Scanner to pull entire file contents into string.
		// "\\Z" is end of string character
		String content;
		try {
			Scanner scanner = new Scanner(menu_file);
			content = scanner.useDelimiter("\\Z").next();
			scanner.close();
		} catch (FileNotFoundException e) {
			throw new MenuDownloadException(e);
		} 
		
		return content;
	}

	// Internal Class Exceptions
	@SuppressWarnings("serial")
	public class UnknownMenuAccessTypeException extends Exception {
		public UnknownMenuAccessTypeException() {
			super("Unknown board access menu type.");
		}
	}
	
	@SuppressWarnings("serial")
	public class MenuDownloadException extends Exception {
		public MenuDownloadException(Exception e) {
			super("Failed to download the board menu.\n" + e.getMessage());
			
		}
	}
}
