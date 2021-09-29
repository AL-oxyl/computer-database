package com.oxyl.service;

import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.oxyl.dao.ComputerDAO;
import com.oxyl.model.Computer;
import com.oxyl.ui.Pagination;
import com.oxyl.validator.IndexValidator;

public class ComputerPageHandlerStrategyService implements GenericPageHandler<Computer>{
		
	
	private ArrayList<Computer> computerPageList;
	private int pageIndex;
	private static int numberComputer = initNumberComputer();
	private static int numberPage = initNumberPage();
	private static final int NUMBER_BUTTON = 5;
	private static final Logger LOGGER = LoggerFactory.getLogger(ComputerPageHandlerStrategyService.class);
	private boolean pageChanged = false;
	private int[] buttonArray = new int[]{1,2,3,4,5};
	
	public ComputerPageHandlerStrategyService() {
		ComputerDAO computers = new ComputerDAO();
		this.computerPageList = computers.getComputerRange(0);
		this.pageIndex = 0;
	}
	
	private static int initNumberComputer() {
		ComputerDAO computers = new ComputerDAO();
		return computers.getComputerCount();
	}
	
	private static int initNumberPage() {
		return (numberComputer/ComputerDAO.NUMBER_RESULT_BY_PAGE)+1;
	}
	
	public static int getNumberComputer() {		
		return numberComputer;
	}
	
	public static int getNumberPage() {		
		return numberPage;
	}
	
	public int[] getButtonArray() {
		return buttonArray;
	}
	
	public void updateButtonArray(int lastPage) {
		if(testLeftBlock()) {
			updateFromLeft();
		} else if (testRightBlock()){
			updateFromRight();
		} else {
			for(int i=0; i<NUMBER_BUTTON/2;i++) {
				System.out.println(buttonArray[i]);
				if(buttonArray[i]-1 == pageIndex ) {
					updateFromRight();
					return;
				}
			}
			updateFromLeft();
		}
	}
	
	
	private void updateFromLeft() {
		for(int i=0; i<NUMBER_BUTTON;i++) {
			buttonArray[i] = pageIndex + 1 + i;
		}
	}
	
	private void updateFromRight() {
		for(int i=NUMBER_BUTTON - 1; i>=0;i--) {
			buttonArray[i] = pageIndex + i - (NUMBER_BUTTON-1) + 1;
		}
	}
	
	
	public void setPageIndex(int index) {
		if(IndexValidator.indexValidator(index)) {
			this.pageIndex = index;
			this.pageChanged = true;
		}
	}

	public int getPageIndex() {
		return pageIndex;
	}

	public ArrayList<Computer> getComputerPageList() {
		if (pageChanged) {
			ComputerDAO computers = new ComputerDAO();
			this.computerPageList = computers.getComputerRange(pageIndex);
		}
		return computerPageList;
	}


	public void setPageList(ArrayList<Computer> computerPageList) {
		this.computerPageList = computerPageList;
	}	
	
	public void handlePage(int result) {
		switch (result) {
			case 1:
				updateInfo(result-1);
				break;
			case 2:
				updateInfo(result+1);
				break;
		}		
	}
	
	public void updateInfo(int entry) {
		ComputerDAO computers = new ComputerDAO();
		int ref = pageIndex;
		setPageIndex(entry);
		if(ref != pageIndex) {
			this.computerPageList = computers.getComputerRange(pageIndex);
			LOGGER.info("Computer page info updated");
		}
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
	
	public boolean testRightBlock() {
		if(pageIndex + NUMBER_BUTTON >= numberPage) {
			return true;
		}
		return false;
	}
	
	public boolean testLeftBlock() {
		if(pageIndex < NUMBER_BUTTON) {
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


