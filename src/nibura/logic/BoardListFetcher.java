package nibura.logic;

import nibura.logic.BoardListDownloader.MenuDownloadException;
import nibura.logic.BoardListDownloader.UnknownMenuAccessTypeException;

public abstract class BoardListFetcher {

	public abstract BoardList getBoardList() throws UnknownMenuAccessTypeException, MenuDownloadException, ParsingErrorException;

}
