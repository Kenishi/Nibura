/**
 * 
 * BoardLink.java
 * 
 * This is the logical unit for a BoardLink. BoardLinks are contained within BoardGroups
 * or directly in a BoardList.
 * 
 * This class implements the BoardListElement. It fully implements all the functions.
 * 
 * A BoardLink will have a SuiteType, Link, and Name. The SuiteType will help distinguish
 * between which type of parser to use when later opening the board or a post. 
 * 
 */

package nibura.logic;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.UUID;

public class BoardLink extends BoardListElement {
	private String name = null;
	private UUID id = null;
	private URL link = null;	
	private SuiteType suite = null;
	
	/** 
	 * Constructor for URL's as Strings.
	 * @param boardName String with board's name
	 * @param url String containing the board's URL link
	 * @param suite An enumerated SuiteType which represents which site this resides under. This affects which parser is used to parse the board and posts.
	 * @throws MalformedURLException Thrown on failure to create the URL. See Java doc for more information.
	 */
	public BoardLink(String boardName, String url, SuiteType suite) throws MalformedURLException {
		this(boardName, new URL(url), suite);
	}
	
	/**
	 * Preferred constructor for board links 
	 * @param boardName String with board's name
	 * @param url URL containing the board's URL.
	 * @param suite An enumberated SuiteType which represents which site this resides under. This affects which parser is used to parse the board and posts.
	 */
	public BoardLink(String boardName, URL url, SuiteType suite)
	{
		name = boardName;
		link = url;
		this.suite = suite;
		
		// Create unique ID
		id = UUID.randomUUID();
	}
	
	/** 
	 * The name of this Board as it appeared on the site's main board list.
	 */
	public String getName() {
		return name;
	}

	/**
	 * Returns the URL as a String.
	 * This is returned as a String as much of the interaction with this object
	 * is in string format for generating HTML.
	 * A URL can be reconstructed by merely using the URL(String) constructor.
	 */
	public String getLink() {
		return link.toString();
	}
	
	/**
	 * Provides a String representation of the object including the UUID.
	 */
	public String toString() {
		String returnString = "";
		returnString += name;
		returnString += " (ID: " + id + ")";
		returnString += " <" + link.toString() + ">\n";
		
		return returnString;
	}

	/**
	 * Provides a String representation of the object without the UUID.
	 */
	@Override
	public String toStringNoID() {
		String returnString = "";
		returnString += name;
		returnString += " <" + link.toString() + ">\n";
		
		return returnString;
	}

	/** 
	 * Returns the UUID as a String representation. 
	 * A UUID can be reconstructed using the UUID.fromString(); static method.
	 */
	@Override
	public String getId() {
		return id.toString();
	}

	/**
	 * Return the associated SuiteType for this board link
	 */
	@Override
	public SuiteType getSuiteType() {
		return suite;
	}

	/**
	 * Compare's the provided target UUID against this BoardLink's ID.
	 * @return Return this element if the UUID matches. Return null if it doesn't match.
	 */
	@Override
	public BoardListElement getElementByID(UUID target) {
		if(id.compareTo(target) == 0)
			return this;
		return null;
	}
}
