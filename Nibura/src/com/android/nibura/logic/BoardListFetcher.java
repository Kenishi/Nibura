package com.android.nibura.logic;

import com.android.nibura.logic.BoardListDownloader.MenuDownloadException;
import com.android.nibura.logic.BoardListDownloader.UnknownMenuAccessTypeException;

public abstract class BoardListFetcher {

	public abstract BoardList getBoardList() throws UnknownMenuAccessTypeException, MenuDownloadException;


}
