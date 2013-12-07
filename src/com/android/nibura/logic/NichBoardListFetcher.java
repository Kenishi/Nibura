package com.android.nibura.logic;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.android.nibura.logic.BoardListDownloader.MenuDownloadException;
import com.android.nibura.logic.BoardListDownloader.UnknownMenuAccessTypeException;

public class NichBoardListFetcher extends BoardListFetcher {
	private final String INTERNAL_2CHMENU_URL = "http://menu.2ch.net/bbsmenu.html";
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
		new NichBoardListFetcher(new URL(menuURL));
	}
	
	// Default Constructor: Use internal URL
	public NichBoardListFetcher() {
	}
	
	public BoardList getBoardList()
			throws UnknownMenuAccessTypeException, MenuDownloadException, ParsingErrorException
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
			newGroup.addElements((BoardListElement[]) boardLinks.toArray());
			
			// Add new group to Board List
			menuList.addElement(newGroup);
		}
		
		return menuList;
	}

	private boolean isInExcludeGroup(String groupName) {
		
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
				groupBoardLinks.add(new BoardLink(linkName, linkURL));
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
		Pattern regex = Pattern.compile("(?<=<BR><BR><B>).+(?=</B><BR>)");
		Matcher match = regex.matcher(groupHTML);
			
		String groupName = null;
		if(match.find()) {
			if(match.groupCount() > 0) {
				groupName = match.group(1);
			}
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
		 *  	"<BR><BR><B>.+?</B><BR>" - "Match the format for a group name"
		 *  	"(.+?" - Open capture group, Match all the text following the group name 
		 *  	"(?=(<BR><BR><B>.+?</B><BR>)" - and lookahead for the start of a new group, make the match lazy
		 *  	"+?)" - Make the lookahead lazy, close out the capture group.
		 * 		Due to the lookahead nature, this will exclude the final "group" on the menu.
		 * 		   This is expected behavior. The final 2ch group is unneeded external web links.
		 */
		Pattern filterPattern = 
				Pattern.compile("<BR><BR><B>.+?</B><BR>(.+?(?=(<BR><BR><B>.+?</B><BR>)+?))");
		Matcher match = filterPattern.matcher(menuHTML);
		
		ArrayList<String> groups = new ArrayList<String>();
		// Retrieve the matches found by the RegEx
		while(match.find()) {
			if(match.groupCount() > 0) {
				groups.add(match.group(0));
			}
		}
		
		return groups;
	}
}
