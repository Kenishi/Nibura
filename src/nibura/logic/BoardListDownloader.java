package nibura.logic;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.Scanner;

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
			throws UnknownMenuAccessTypeException, MenuDownloadException {
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
	
	private String getContentByURL() throws MenuDownloadException {
		String content = "";
		try {
			content += menu_URL.getContent();
		} catch (IOException e) {
			throw new MenuDownloadException(e);
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
