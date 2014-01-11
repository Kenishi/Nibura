package nibura.logic;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.UUID;

public class ThreadList implements Iterable<ThreadLink> {
	private ArrayList<ThreadLink> threadtList = null;
	
	public ThreadList() {
		threadtList = new ArrayList<ThreadLink>();
	}
	
	@Override
	public String toString() {
		return threadtList.toString();
	}
	
	public String toString_withID() {
		String out = "";
		for(ThreadLink link : threadtList)
			out += link.toString_withID() + "\n";
		
		return out;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof ThreadList) {
			ThreadList target = (ThreadList)obj;
			return target.isArrayEqual(threadtList);
		}
		else
			return false;
	}
	
	@Override
	public Iterator<ThreadLink> iterator() {
		return threadtList.iterator();
	}
	
	public ThreadLink getThreadLinkByID(UUID id) {
		for(ThreadLink link : threadtList) {
			if(link.getThreadLinkByID(id) != null)
				return link;
		}
		return null;
	}
	
	// Protected Methods
	
	protected boolean isArrayEqual(ArrayList<ThreadLink> target) {
		if(threadtList.equals(target))
			return true;
		else
			return false;
	}
	protected void addThreadLink(ThreadLink link) {
		threadtList.add(link);
	}
	
	// Private Methods
}
