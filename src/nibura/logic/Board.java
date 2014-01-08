package nibura.logic;

import nibura.logic.BoardListElement.SuiteType;

public class Board {
	private PostList postList = null;
	
	public Board(BoardLink link) {
		SuiteType linkType = link.getSuiteType();
		if(linkType == SuiteType.NICH_SUITE) {
			NichBoardFetcher boardFetcher = new NichBoardFetcher(link);
			postList = boardFetcher.getPostList();
		}
	}
	
	@SuppressWarnings("serial")
	public class InvalidBoardException extends Exception {
		public InvalidBoardException(){
			super("Failed to load board because link was to a non-board.");
		}
	}
}
