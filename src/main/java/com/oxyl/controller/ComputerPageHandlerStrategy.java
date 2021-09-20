package com.oxyl.controller;

import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.oxyl.dao.ComputerDAO;
import com.oxyl.model.Computer;
import com.oxyl.ui.Pagination;

public class ComputerPageHandlerStrategy implements GenericPageHandler<Computer>{
	
	private ArrayList<Computer> computerPageList;
	private int pageIndex;
	private int numberPage;
	private static final Logger LOGGER = LoggerFactory.getLogger(ComputerPageHandlerStrategy.class);

	
	public ComputerPageHandlerStrategy(ArrayList<Computer> computerPageList, int numberComputer) {
		this.setPageList(computerPageList);
		this.pageIndex = 0;
		this.numberPage = (numberComputer/ComputerDAO.NUMBER_RESULT_BY_PAGE)+1;
	}

	public int getPageIndex() {
		return pageIndex;
	}


	public ArrayList<Computer> getComputerPageList() {
		return computerPageList;
	}


	public void setPageList(ArrayList<Computer> computerPageList) {
		this.computerPageList = computerPageList;
	}


	public int getNumberPage() {
		return numberPage;
	}	
	
	public void handlePage(int result) {
		updateInfo(result);
		
	}
	
	public void updateInfo(int entry) {
		ComputerDAO computers = new ComputerDAO();
		switch (entry) {
			case 1:
				pageIndex--;
				break;
			case 2:
				pageIndex++;
				break;
		}	
		this.computerPageList = computers.getComputerRange(pageIndex);
		LOGGER.info("Computer page info updated");

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

	@Override
	public ArrayList<Computer> getPageList() {
		return computerPageList;
	}
	
	public Pagination getPageState() {
		if (testLeft()) {
			return Pagination.LEFT;
		} else if (testRight()){
			return Pagination.RIGHT;
		}
		return Pagination.MIDDLE;
	}
}


