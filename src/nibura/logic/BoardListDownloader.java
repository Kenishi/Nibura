package nibura.logic;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.Scanner;

import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

public class BoardListDownloader {
	private File menu_file = null; // Saved file with menu HTML
	private URL menu_URL = null; // URL to get HTML from
	
	protected BoardListDownloader(File file) throws FileNotFoundException {
		if( !file.exists() ) {
			throw new FileNotFoundException();
		}
		menu_file = file;
	}
	
	protected BoardListDownloader(URL url) {
		menu_URL = url;
	}
	
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
	
	private String getContentByURL2() throws MenuDownloadException {
		String content = "";
		byte[] dataArray = new byte[128];
		URLConnection connection = null;
		try {
			connection = menu_URL.openConnection();
			int read = connection.getInputStream().read(dataArray);
			while(read >= 0) {
				content += new String(dataArray, Charset.forName("SJIS"));
				Arrays.fill(dataArray, (byte)0);
				read = connection.getInputStream().read(dataArray);				
			}
		} catch (IOException e) {
			throw new MenuDownloadException(e);
		}
		return content;
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
