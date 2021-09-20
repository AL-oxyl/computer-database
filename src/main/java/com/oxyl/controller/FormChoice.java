package com.oxyl.controller;

public enum FormChoice {
	LISTCOMPUTER(1),
	LISTCOMPANY(2),
	COMPUTERINFO(3),
	CREATECOMPUTER(4),
	UPDATECOMPUTER(5),
	DELETECOMPUTER(6),
	QUIT(7);
	
	int id;
	
	private FormChoice(int id) {
		this.id = id;
	}
	
	public int getId() {
		return id;
	}
}
 