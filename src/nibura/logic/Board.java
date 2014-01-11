package nibura.logic;

import java.io.FileNotFoundException;
import java.net.MalformedURLException;
import java.util.Iterator;
import java.util.UUID;

import nibura.logic.BoardListElement.SuiteType;

public class Board implements Iterable<ThreadLink> {
	private ThreadList threadList = null;
	
	public Board(BoardLink link) throws InvalidSuiteTypeException, ParsingErrorException, InvalidBoardException, MalformedURLException, FileNotFoundException {
		SuiteType linkType = link.getSuiteType();
		if(linkType == SuiteType.NICH_SUITE) {
			NichBoardFetcher boardFetcher = new NichBoardFetcher(link);
			threadList = boardFetcher.getThreadList();
		}
	}
	
	public Iterator<ThreadLink> iterator() {
		return threadList.iterator();
	}
	
	public ThreadLink getThreadLinkByID(UUID id) {
		ThreadLink link = threadList.getThreadLinkByID(id);
		return link;
	}
	
	@Override
	public String toString() {
		String out = "";
		for(ThreadLink link : threadList)
			out += link.toString() + "\n";

		return out;
	}
	
	public String toString_withID() {
		String out = "";
		for(ThreadLink link : threadList)
			out += link.toString_withID() + "\n";
		
		return out;
	}
}
