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

	// Protected Methods
	
	
	// Private Methods

}
