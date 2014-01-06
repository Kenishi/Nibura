package nibura.logic;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import nibura.logic.BoardListDownloader.MenuDownloadException;
import nibura.logic.BoardListDownloader.UnknownMenuAccessTypeException;
import nibura.logic.BoardListElement.SuiteType;

import org.apache.http.client.ClientProtocolException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import org.apache.http.client.ClientProtocolException;

public class NichBoardListFetcher extends BoardListFetcher {
	private static final String INTERNAL_2CHMENU_URL = "http://menu.2ch.net/bbsmenu.html";
	private final String EXLUDED_GROUPS[] = {
			"チャット",
			"運営案内",
			"ツール類",
			"BBSPINK",
			"まちＢＢＳ",
			"他のサイト" };
	
	private BoardListDownloader boardMenuAccessor = null;
	
	public NichBoardListFetcher(File boardListFile_in) throws FileNotFoundException {
		boardMenuAccessor = new BoardListDownloader(boardListFile_in);
	}
	
	// Use specified link to get 2ch Board Menu HTML
	public NichBoardListFetcher(URL menuURL) {
		boardMenuAccessor = new BoardListDownloader(menuURL);	
	}
	
	public NichBoardListFetcher(String menuURL) throws MalformedURLException {
		this(new URL(menuURL));
	}
	
	// Default Constructor: Use internal URL
	public NichBoardListFetcher() throws MalformedURLException {
		this(INTERNAL_2CHMENU_URL);
	}
	
	public BoardList getBoardList() 
			throws UnknownMenuAccessTypeException, MenuDownloadException, ParsingErrorException, URISyntaxException, IOException
	{
		String content = boardMenuAccessor.getBoardMenuHTML();
		BoardList NichBoardList = parseContent(content);		
				
		return NichBoardList;
	}
	
	private BoardList parseContent(String menuHTML) throws ParsingErrorException {
		BoardList menuList = new BoardList();
		
		// First Pass: Extract the board groups from the 2ch menu
		ArrayList<String> filteredGroups = filterForGroupsHTML(menuHTML);
		
		// Second Pass: Extract groups and links to data structures
		Iterator<String> groupsIterator = filteredGroups.iterator();
		
		// Iterate through the group blocks and build the boardList
		while(groupsIterator.hasNext()) {
			String groupHTML = groupsIterator.next();
			
			// Get the group's name and links from the block
			String groupName = getGroupName(groupHTML);
			if(isInExcludeGroup(groupName)) {
				continue;
			}
			ArrayList<BoardLink> boardLinks = getGroupLinks(groupHTML);
			
			//Create the group and add the links
			BoardGroup newGroup = new BoardGroup(groupName);
			newGroup.addElements(
					boardLinks.toArray(new BoardListElement[boardLinks.size()])  // Supply an array to get an array from ArrayList
					); 
			
			// Add new group to Board List
			menuList.addElement(newGroup);
		}
		
		return menuList;
	}

	private boolean isInExcludeGroup(String groupName) {
		for(final String excludedName : EXLUDED_GROUPS) {
			if(groupName.equals(excludedName)) {
				return true;
			}
		}
		return false;
	}

	private ArrayList<BoardLink> getGroupLinks(String groupHTML) throws ParsingErrorException {
		Document block = Jsoup.parse(groupHTML);
		Elements extractedLinks = block.select("a[href]");
		Iterator<Element> linkIterator = extractedLinks.iterator();
		
		ArrayList<BoardLink> groupBoardLinks = new ArrayList<BoardLink>();
		while(linkIterator.hasNext()) {
			Element link = linkIterator.next();
			
			String linkName = link.text();
			String linkURL = link.attr("abs:href");
			
			try {
				groupBoardLinks.add(new BoardLink(linkName, linkURL, SuiteType.NICH_SUITE));
			} catch (MalformedURLException e) {
				throw new ParsingErrorException("Bad URL in parsing group block.", groupHTML);
			}
		}
		
		return groupBoardLinks;
	}
	
	/**
	 * Extracts the group name for a group block of HTML
	 * @param groupHTML
	 * @return Returns a string containing the group name
	 * @throws ParsingErrorException
	 */
	private String getGroupName(String groupHTML) throws ParsingErrorException {
		Pattern regex = Pattern.compile("(?<=(?i)<BR><BR><B>(?-i)).+(?=(?i)</B><BR>(?-i))");
		Matcher match = regex.matcher(groupHTML);
			
		String groupName = null;
		if(match.find()) {
			groupName = match.group(0);
		}
		else {
			groupName = "!--ERROR--!";
			throw new ParsingErrorException("Error parsing block for group name.",groupHTML);
		}
		return groupName;
	}

	/**
	 * Filters the Menu HTML for the groups and puts the group HTML in an array 
	 */
	private ArrayList<String> filterForGroupsHTML(String menuHTML) {
		/**
		 *  RegEx Explanation:
		 *  	"(?i)" - Turn on case insensitive mode
		 *  	"<BR><BR><B>.+?</B><BR>" - Match the format for a group name
		 *  	"(?-i)" - Turn off case insensitive mode
		 *  	"(.|\\r|\\n)" - Match all the text following the group name incl. newlines 
		 *  	"(?=((?i)<BR><BR><B>.+?</B><BR>(?-i))" - and lookahead for the start of a new group, make the match lazy and use case-insensitive mode
		 *  	"+?)" - Make the lookahead lazy, close out the capture group.
		 * 		Due to the lookahead nature, this will exclude the final "group" on the menu.
		 * 		   This is expected behavior. The final 2ch group is unneeded external web links.
		 */
		Pattern filterPattern = 
				Pattern.compile("(?i)(?:<BR><BR><B>(.+?)</B><BR>)\\s*((?:<A HREF=.*?>.*?</A><BR>\\s*)+)");
		Matcher match = filterPattern.matcher(menuHTML);
		
		ArrayList<String> groups = new ArrayList<String>();
		// Retrieve the matches found by the RegEx
		while(match.find()) {
			if(match.groupCount() > -1) {
				groups.add(match.group(0));
			}
		}		
		return groups;
	}
}
