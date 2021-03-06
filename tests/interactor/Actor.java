package interactor;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.util.UUID;

import javax.swing.JOptionPane;

import nibura.logic.Board;
import nibura.logic.BoardGroup;
import nibura.logic.BoardLink;
import nibura.logic.BoardList;
import nibura.logic.BoardListDownloader.MenuDownloadException;
import nibura.logic.BoardListDownloader.UnknownMenuAccessTypeException;
import nibura.logic.BoardListElement;
import nibura.logic.InvalidBoardException;
import nibura.logic.InvalidSuiteTypeException;
import nibura.logic.NichBoardListFetcher;
import nibura.logic.ParsingErrorException;
import nibura.logic.ThreadLink;

public class Actor {
	enum COMMAND {
		SHOW_BOARDLIST,
		SHOW_BOARD,
		SHOW_POST,
		UNNOWN, EXIT;
	}
	
	BoardList boardList = null;
	Board board = null;
	
	public Actor() throws MalformedURLException, FileNotFoundException {
		while(true) {
			COMMAND cmd = commandMenu();
			if(cmd == COMMAND.SHOW_BOARDLIST) {
				try {
					loadBoardList();
				} catch (MalformedURLException e) {
					e.printStackTrace();
				}
			}
			else if(cmd == COMMAND.SHOW_BOARD) {
				try {
					doBoardLoad();
				} catch (InvalidSuiteTypeException | ParsingErrorException
						| InvalidBoardException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			else if(cmd == COMMAND.SHOW_POST) {
				doThreadLoad();
			}
			else if(cmd == COMMAND.EXIT) {
				break;
			}
		}
	}

	private void doThreadLoad() {
		String postID = requestThreadID();
		
		if(postID.equals("-1"))
			return;
		
		UUID targetID = UUID.fromString(postID);
		ThreadLink threadLink = board.getThreadLinkByID(targetID);
		if(postLink == null) {
			System.out.println("Post link not found. Check that input was correct.");
			return;
		}
		Thread thread = new Thread(threadLink);
		System.out.println(thread.toString());
		
	}

	private void doBoardLoad() throws InvalidSuiteTypeException, ParsingErrorException, InvalidBoardException, MalformedURLException, FileNotFoundException {
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
			System.out.println(board.toString_withID());
		}
		else {
			System.out.println("Matched element of Unknown Type.");
		}			
	}

	private String requestBoardID() {
		String id = "-1";
		String out = "Input board ID to load (-1 to abort): ";
		System.out.print(out);
		id = JOptionPane.showInputDialog("Input Link ID: ");
		
		return id;
	}

	private COMMAND commandMenu() {
		String out = "";
		out += "1: Load/Show BoardList\n";
		if(boardList != null)
			out += "2: Load/Show Board\n";
		if(board != null)
			out += "3: Load Post\n";
		out += "0: Exit";
		System.out.println(out);
		
		String input = JOptionPane.showInputDialog("Input (1,2,3,0):");
		if(input == null) input = "0";
		Integer response = Integer.decode(input);
		if(response == 1)
			return COMMAND.SHOW_BOARDLIST;
		else if(response == 2)
			return COMMAND.SHOW_BOARD;
		else if(response == 3)
			return COMMAND.SHOW_POST;
		else if(response == 0)
			return COMMAND.EXIT;
		else
			return COMMAND.UNNOWN;
	}
	
	private void loadBoardList() throws MalformedURLException {
		NichBoardListFetcher fetcher = new NichBoardListFetcher();
		boardList = null;
		try {
			boardList = fetcher.getBoardList();
		} catch (UnknownMenuAccessTypeException | MenuDownloadException
				| ParsingErrorException | URISyntaxException | IOException e) {
			e.printStackTrace();
		}
		System.out.println(boardList.toString());
	}
	
}
