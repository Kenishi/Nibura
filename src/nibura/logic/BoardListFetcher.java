package nibura.logic;

import java.io.IOException;
import java.net.URISyntaxException;

import nibura.logic.BoardListDownloader.MenuDownloadException;
import nibura.logic.BoardListDownloader.UnknownMenuAccessTypeException;

public abstract class BoardListFetcher {

	public abstract BoardList getBoardList() throws UnknownMenuAccessTypeException, MenuDownloadException, ParsingErrorException, URISyntaxException, IOException;

}
