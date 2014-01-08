package nibura.logic;

public class Board {
	public Board(BoardLink link) {
		x`
	}
	
	@SuppressWarnings("serial")
	public class InvalidBoardException extends Exception {
		public InvalidBoardException(){
			super("Failed to load board because link was to a non-board.");
		}
	}
}
