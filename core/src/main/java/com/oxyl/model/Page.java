package com.oxyl.model;

import java.util.List;

public class Page {
	List<Computer> computerList;
	Boolean testLeft;
	Boolean testRight;
	Long numberComputer;
	Long numberPage;
	Boolean testNumber;
	
	public Page(List<Computer> computerList, Boolean testLeft, Boolean testRight, Long numberComputer, Long numberPage, Boolean testNumber) {
		this.computerList = computerList;
		this.testLeft = testLeft;
		this.testRight = testRight;
		this.numberComputer = numberComputer;
		this.numberPage = numberPage;
		this.testNumber = testNumber;	
	}
	
	public List<Computer> getComputerList() {
		return computerList;
	}
	
	public Boolean isLeft()  {
		return testLeft;
	}
	
	public Boolean isRight() {
		return testRight;
	}
	
	public Long getNumberComputer() {
		return numberComputer;
	}
	
	public Long getNumberPage() {
		return numberPage;
	}

	public Boolean isUnique() {
		return testNumber;
	}
}
