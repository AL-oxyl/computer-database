package com.oxyl.model;


public class Company {

	private Integer id;
	private String name;
	
	public Company() {
		this.id = -1;
		this.name = "";
	}
	
	public Company(int id, String name) {
		this.id = id;
		this.name = name;
	}
	
	public Integer getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	@Override
	public String toString() {
		return name;
	}
}
