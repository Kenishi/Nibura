package nibura.logic;

@SuppressWarnings("serial")
public class ParsingErrorException extends Exception {
	public ParsingErrorException(String msg, String block) {
		super(msg + "\nHTML Block:\n" + block);
	}

	public ParsingErrorException(String msg) {
		super(msg);
	}
}
