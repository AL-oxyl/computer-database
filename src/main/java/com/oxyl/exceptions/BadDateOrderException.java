package com.oxyl.exceptions;

public class BadDateOrderException extends Exception{
		
	private static final long serialVersionUID = 1L;
		
	public BadDateOrderException() {
	}
		
	public BadDateOrderException(String text) {
		super(text);
	}
}
