package com.android.nibura.logic;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.MalformedURLException;
import java.net.URL;

import com.android.nibura.logic.BoardListDownloader.MenuDownloadException;
import com.android.nibura.logic.BoardListDownloader.UnknownMenuAccessTypeException;


public class NichBoardListFetcher extends BoardListFetcher {
	private final String INTERNAL_2CHMENU_URL = "http://menu.2ch.net/bbsmenu.html";
	
	private BoardListDownloader boardMenuAccessor = null;
	
	public NichBoardListFetcher(File boardListFile_in) throws FileNotFoundException {
		boardMenuAccessor = new BoardListDownloader(boardListFile_in);
	}
	
	// Use specified link to get 2ch Board Menu HTML
	public NichBoardListFetcher(URL menuURL) {
		boardMenuAccessor = new BoardListDownloader(menuURL);	
	}
	
	public NichBoardListFetcher(String menuURL) throws MalformedURLException {
		new NichBoardListFetcher(new URL(menuURL));
	}
	
	// Default Constructor: Use internal URL
	public NichBoardListFetcher() {
	}
	
	public BoardList getBoardList()
			throws UnknownMenuAccessTypeException, MenuDownloadException
	{
		String content = boardMenuAccessor.getBoardMenuHTML();
		BoardList NichBoardList = new BoardList();
		
		
		
		return NichBoardList;
	}
}
