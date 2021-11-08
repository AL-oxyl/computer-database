package com.oxyl.exceptions;

public class InvalidComputerException extends Exception {

	private static final long serialVersionUID = 1L;
		
	public InvalidComputerException() {
	}
			
	public InvalidComputerException(String text) {
		super(text);
	}
}
