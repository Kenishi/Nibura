/**
 * 
 * BoardGroup.java
 * 
 * BoardGroup is the logical object for grouping Board Links on the Board List.
 * 
 * It implements the BoardListElement interface but will not return a link when
 * getLink() is called on it, it will merely return NO_BOARD_LINK.
 * 
 * This class will also not return a SuiteType or an ID as BoardGroups are considered
 * to be neutral items. Logically, you can hold board links to different types of boards
 * under a single group. (Ex: Having a group "Favorites" for different links).
 * 
 */
package nibura.logic;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.UUID;

public class BoardGroup extends BoardListElement implements Iterable<BoardListElement> {
	private String groupName = null;
	private ArrayList<BoardListElement> boardLinksArray = new ArrayList<BoardListElement>();
	
	public BoardGroup(String name) {
		groupName = name;
	}
	
	public String getName() {
		return groupName;
	}

	public String getLink() {
		return BoardListElement.NO_BOARD_LINK;
	}
	
	@Override
	public String toString() {
		String returnString = "**" + groupName + "\n";
		Iterator<BoardListElement> links = boardLinksArray.iterator();
		while(links.hasNext()) {
			BoardListElement element = links.next(); 
			returnString += element.toString();
		}
		
		return returnString;
	}
	
	@Override
	public String toStringNoID() {
		String returnString = "**" + groupName + "\n";
		for(BoardListElement element : this) {
			returnString += element.toStringNoID();
		}
		
		return returnString;
	}

	public Iterator<BoardListElement> iterator() {
		return boardLinksArray.iterator();
	}

	@Override
	public String getId() {
		return null;
	}

	@Override
	public SuiteType getSuiteType() {
		return null;
	}
	
	/**
	 * Searches board elements looking for id
	 * @return Returns element on match. Otherwise, returns null if not found.
	 */
	@Override
	public BoardListElement getElementByID(UUID id) {
		for(BoardListElement element : boardLinksArray) {
			BoardListElement match = element.getElementByID(id);
			if(match != null)
				return match;
		}		
		return null;
	}
	
	// Protected Members
	protected void addElement(BoardListElement element) {
		boardLinksArray.add(element);
	}
	protected void addElement(BoardListElement element, int index) {
		boardLinksArray.add(index, element);
	}

	protected void addElements(BoardListElement[] elements) {
		for(int index = 0; index < elements.length; index++) {
			boardLinksArray.add(elements[index]);
		}
	}
	
	
	/**
	 * Returns the number of elements in the current group. Count does not include embedded groups
	 * @return Number of elements in this group
	 */
	protected int debug_getElementCount() {
		return boardLinksArray.size();
	}
	protected Iterator<BoardListElement> debug_iterator() {
		return boardLinksArray.iterator();
	}
	protected BoardListElement debug_getElement(int index) {
		return boardLinksArray.get(index);
	}
	// Private Members

}
