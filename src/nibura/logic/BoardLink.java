package nibura.logic;

import java.net.MalformedURLException;
import java.net.URL;

public class BoardLink extends BoardListElement {
	private String name = null;
	private URL link = null;
	
	public BoardLink(String boardName, String url) throws MalformedURLException {
		name = boardName;
		link = new URL(url);
	}
	
	public BoardLink(String boardName, URL url)
	{
		name = boardName;
		link = url;
	}
	
	public String getName() {
		return name;
	}

	public String getLink() {
		return link.toString();
	}
	
	public String toString() {
		String returnString = "";
		
		returnString += name;
		returnString += " <" + link.toString() + ">\n";
		
		return returnString;
	}
}
