package com.oxyl.dto;

import java.util.List;

public class PageDTO {
	List<ComputerDTO> computerList;
	Boolean testLeft;
	Boolean testRight;
	Long numberComputer;
	Long numberPage;
	Boolean testNumber;
	
	public PageDTO(List<ComputerDTO> computerList, Boolean testLeft, Boolean testRight, Long numberComputer, Long numberPage, Boolean testNumber) {
		this.computerList = computerList;
		this.testLeft = testLeft;
		this.testRight = testRight;
		this.numberComputer = numberComputer;
		this.numberPage = numberPage;
		this.testNumber = testNumber;	
	}
	
	public PageDTO(List<ComputerDTO> computerList) {
		this.computerList = computerList;
	}
	
	public List<ComputerDTO> getComputerList() {
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
