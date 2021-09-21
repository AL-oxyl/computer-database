package com.oxyl.service;

import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.oxyl.controller.GenericPageHandler;
import com.oxyl.dao.ComputerDAO;
import com.oxyl.model.Computer;
import com.oxyl.ui.Pagination;

public class ComputerPageHandlerStrategyService implements GenericPageHandler<Computer>{
	
	private ArrayList<Computer> computerPageList;
	private int pageIndex;
	private int numberPage;
	private static final Logger LOGGER = LoggerFactory.getLogger(ComputerPageHandlerStrategyService.class);

	
	public ComputerPageHandlerStrategyService() {
		ComputerDAO computers = new ComputerDAO();
		this.computerPageList = computers.getComputerRange(0);
		this.pageIndex = 0;
		int numberResult = computers.getComputerCount();
		this.numberPage = (numberResult/ComputerDAO.NUMBER_RESULT_BY_PAGE)+1;
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


