package com.oxyl.controller;

import java.util.ArrayList;
import com.oxyl.dao.Computers;
import com.oxyl.model.Computer;

public class ComputerPageHandler {
	
	private static ComputerPageHandler instance;
	private ArrayList<Computer> computerPageList;
	private int pageIndex;
	private int numberPage;
	
	private ComputerPageHandler(ArrayList<Computer> computerPageList, int numberPage) {
		this.setComputerPageList(computerPageList);
		this.pageIndex = 0;
		this.numberPage = numberPage/Computers.NUMBER_RESULT_BY_PAGE;
 	}
	
	
	public static ComputerPageHandler getInstance(ArrayList<Computer> in, int numberPage) {
		if (instance == null) {
			instance = new ComputerPageHandler(in,numberPage);
		}
		return instance;
	}
	
	public static ComputerPageHandler getInstance() {
		if (instance == null) {
			System.out.println("There is no instance");
			System.out.println("Please use getInstance(ArrayList<Computer> in, int numberPage)");
		}
		return instance;		
	}

	public int getPageIndex() {
		return pageIndex;
	}


	public ArrayList<Computer> getComputerPageList() {
		return computerPageList;
	}


	public void setComputerPageList(ArrayList<Computer> computerPageList) {
		this.computerPageList = computerPageList;
	}


	public int getNumberPage() {
		return numberPage;
	}	
	
	public void handlePage(int result) {
		updateInfo(result);
		
	}
	
	private void updateInfo(int entry) {
		Computers computers = new Computers();
		switch (entry) {
			case 1:
				pageIndex--;
				break;
			case 2:
				pageIndex++;
				break;
		}	
		this.computerPageList = computers.getComputerRange(pageIndex);
	}
	
	public boolean testLeft() {
		if (pageIndex == 0) {
			return true;
		}
		return false;
	}
	

	public boolean testRight() {
		if (pageIndex == numberPage-1) {
			return true;
		}
		return false;
	}
}


