/**
 * BoardList.java
 * 
 * This is the logical object for the board list. It holds and maintains the 
 * board list that is presented on the front. 
 * 
 * The BoardList is constructed of BoardListElements which may be either a BoardGroup
 * or a BoardLink. Generally, BoardGroups hold BoardLinks but can hold other BoardGroups
 * for nesting/organization purposes.
 * 
 */

package nibura.logic;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.UUID;

public class BoardList implements Iterable<BoardListElement> {
	private ArrayList<BoardListElement> listArray = new ArrayList<BoardListElement>(); 
	
	public BoardList() {}

	// Public Methods
	/**
	 * Return an iterator of BoardListElement
	 */
	public Iterator<BoardListElement> iterator() {
		return listArray.iterator();
	}
	
	/**
	 * Return a complete String representation of the entire BoardList.
	 * BoardLinks will have their UUID's displayed.
	 */
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
	
	/**
	 * Return a complete String representation of the entire BoardList.
	 * BoardLinks will NOT have their UUID's displayed.
	 */
	public String toStringNoID() {
		String returnStr = "";
		
		for(BoardListElement element : this) {
			returnStr += element.toStringNoID();
		}
		return returnStr;
	}
	
	/**
	 * Retrieves the element/link matching the supplied UUID
	 * @param id A UUID representating the element being looked for
	 * @return Returns a BoardListElement matching the id. Returns null if no match is found.
	 */
	public BoardListElement getElementByUUID(UUID id) {
		for(BoardListElement element : listArray) {
			BoardListElement match = element.getElementByID(id);
			if(match != null)
				return match;
		}
		return null;
	}
	// Protected Methods
	/**
	 * Add the BoardListElement to the board list.
	 * @param element A class implementing the BoardListElement type that will be added to be added to the list.
	 */
	protected void addElement(BoardListElement element) {
		listArray.add(element);
	}

	// Private Methods

}
