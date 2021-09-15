package com.oxyl.controller;

public enum FormType {
	MENU(1),
	PAGE(2);
	
	private int index;
	
	private FormType(int i) {
		this.index = i;
	}
	
	public int getIndex() {
		return index;
	}
}
