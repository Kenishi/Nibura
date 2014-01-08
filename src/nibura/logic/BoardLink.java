package nibura.logic;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.UUID;

public class BoardLink extends BoardListElement {
	private String name = null;
	private UUID id = null;
	private URL link = null;	
	private SuiteType suite = null;
	
	public BoardLink(String boardName, String url, SuiteType suite) throws MalformedURLException {
		this(boardName, new URL(url), suite);
	}
	
	public BoardLink(String boardName, URL url, SuiteType suite)
	{
		name = boardName;
		link = url;
		
		// Create unique ID
		id = UUID.randomUUID();
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
		returnString += " (ID: " + id + ")";
		returnString += " <" + link.toString() + ">\n";
		
		return returnString;
	}

	@Override
	public String getId() {
		return id.toString();
	}

	@Override
	public SuiteType getSuiteType() {
		return suite;
	}

	@Override
	public BoardListElement getElementByID(UUID target) {
		if(id.compareTo(target) == 0)
			return this;
		return null;
	}

	@Override
	public String toStringNoID() {
		String returnString = "";
		returnString += name;
		returnString += " <" + link.toString() + ">\n";
		
		return returnString;
	}
}
