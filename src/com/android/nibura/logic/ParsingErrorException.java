package com.android.nibura.logic;

public class ParsingErrorException extends Exception {
	public ParsingErrorException(String msg, String block) {
		super(msg + "\nHTML Block:\n" + block);
	}
}
