package interactor;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.util.UUID;

import nibura.logic.Board;
import nibura.logic.BoardGroup;
import nibura.logic.BoardLink;
import nibura.logic.BoardList;
import nibura.logic.BoardListDownloader;
import nibura.logic.BoardListDownloader.MenuDownloadException;
import nibura.logic.BoardListDownloader.UnknownMenuAccessTypeException;
import nibura.logic.BoardListElement;
import nibura.logic.BoardListFetcher;
import nibura.logic.NichBoardListFetcher;
import nibura.logic.ParsingErrorException;

public class Actor {
	enum COMMAND {
		SHOW_BOARDLIST,
		SHOW_BOARD,
		SHOW_POST,
		UNNOWN;
	}
	
	BoardList boardList = null;
	Board board = null;
	
	public Actor() {
		while(true) {
			COMMAND cmd = commandMenu();
			if(cmd == COMMAND.SHOW_BOARDLIST) {
				loadBoardList();
			}
			else if(cmd == COMMAND.SHOW_BOARD) {
				doBoardLoad();
			}
			else if(cmd == COMMAND.SHOW_POST) {
				doPostLoad();
			}
		}
	}

	private void doBoardLoad() {
		String boardID = requestBoardID();
		if(boardID.equals("-1")) // Abort board load
			return;
		UUID targetID = UUID.fromString(boardID);
		BoardListElement link = boardList.getElementByUUID(targetID);
		if(link == null) {
			System.out.print("Board not found. Check that input was correct.");
			return;
		}
		if(link instanceof BoardGroup) {
			System.out.println("Matched element was a board group. Should not happen.");
			return;
		}
		else if(link instanceof BoardLink) {
			board = new Board((BoardLink)link);
			System.out.println(board.toString());
		}
		else {
			System.out.println("Matched element of Unknown Type.");
		}			
	}

	private String requestBoardID() {
		String id = "-1";
		String out = "Input board ID to load (-1 to abort): ";
		System.out.print(out);
		id = System.console().readLine();
		
		return id;
	}

	private COMMAND commandMenu() {
		String out = "";
		out += "1: Load/Show BoardList\n";
		if(boardList != null)
			out += "2: Load/Show Board";
		if(board != null)
			out += "3: Load Post";
		out += "0: Exit";
		System.out.println(out);
		
		String input = System.console().readLine();
		Integer response = Integer.decode(input);
		if(response == 1)
			return COMMAND.SHOW_BOARDLIST;
		else if(response == 2)
			return COMMAND.SHOW_BOARD;
		else if(response == 3)
			return COMMAND.SHOW_POST;
		else
			return COMMAND.UNNOWN;
	}
	
	private void loadBoardList() throws MalformedURLException {
		NichBoardListFetcher fetcher = new NichBoardListFetcher();
		BoardList boardList = null;
		try {
			boardList = fetcher.getBoardList();
		} catch (UnknownMenuAccessTypeException | MenuDownloadException
				| ParsingErrorException | URISyntaxException | IOException e) {
			e.printStackTrace();
		}
		System.out.println(boardList.toString());
	}
	
}
