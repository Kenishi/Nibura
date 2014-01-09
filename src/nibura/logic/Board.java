package nibura.logic;

import java.net.MalformedURLException;

import nibura.logic.BoardListElement.SuiteType;

public class Board {
	private PostList postList = null;
	
	public Board(BoardLink link) throws InvalidSuiteTypeException, ParsingErrorException, InvalidBoardException, MalformedURLException {
		SuiteType linkType = link.getSuiteType();
		if(linkType == SuiteType.NICH_SUITE) {
			NichBoardFetcher boardFetcher = new NichBoardFetcher(link);
			postList = boardFetcher.getPostList();
		}
	}
}
