package com.oxyl.exceptions;

public class NameException extends Exception{
	
	private static final long serialVersionUID = 1L;
	
	public NameException() {
	}
	
	public NameException(String text) {
		super(text);
	}
}
