package com.android.nibura.logic;

import java.io.File;


public class NichBoardListFetcher extends BoardListFetcher {
	
	private File boardListFile = null; 
	
	public NichBoardListFetcher(File boardListFile_in) {
		boardListFile = boardListFile_in;
	}

	public BoardList getBoardList()
	{
		BoardList NichBoardList = new BoardList();
		
		return NichBoardList;
	}
}
