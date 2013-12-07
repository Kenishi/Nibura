package com.android.nibura.logic;

import java.util.ArrayList;
import java.util.Iterator;

public class BoardList implements Iterable<BoardListElement> {
	private ArrayList<BoardListElement> listArray = new ArrayList<BoardListElement>(); 
	
	public BoardList() {}

	// Public Methods
	public Iterator<BoardListElement> iterator() {
		return listArray.iterator();
	}
	
	@Override
	public String toString() {
		String returnStr = "";
		
		Iterator<BoardListElement> elementIterator = iterator();
		while(elementIterator.hasNext()) {
			BoardListElement element = elementIterator.next();
			returnStr += element.toString();
		}
		
		return returnStr;
	}

	// Protected Methods
	protected void addElement(BoardListElement element) {
		listArray.add(element);
	}
	
	// Private Methods

}
