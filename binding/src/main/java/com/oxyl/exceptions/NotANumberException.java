package com.oxyl.exceptions;

public class NotANumberException extends Exception{
	
	private static final long serialVersionUID = 1L;
	
	public NotANumberException() {
	}
		
	public NotANumberException(String text) {
		super(text);
	}
}
