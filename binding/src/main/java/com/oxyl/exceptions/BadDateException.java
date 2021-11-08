package com.oxyl.exceptions;

public class BadDateException extends Exception {
	
	private static final long serialVersionUID = 1L;
	
	public BadDateException() {
	}
		
	public BadDateException(String text) {
		super(text);
	}
}
