package com.android.nibura.logic;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Iterator;

public class BoardGroup implements BoardListElement {
	
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
			returnString += links.toString();
		}
		
		return returnString;
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
	 * @return Number of eleemnts in this group
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
