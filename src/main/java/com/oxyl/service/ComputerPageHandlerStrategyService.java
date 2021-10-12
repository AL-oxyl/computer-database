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
	private int localNumberComputer;
	private int localNumberPage;
	private boolean pageChanged = false;
	private int[] buttonArray = new int[]{1,2,3,4,5};
	private State state;
	private String searchedEntry = "";
	
	public ComputerPageHandlerStrategyService() {
		ComputerDAO computers = new ComputerDAO();
		this.computerPageList = computers.getComputerRange(0);
		this.pageIndex = 0;
		this.state = State.NORMAL;
		this.localNumberComputer = numberComputer;
	}
	
	public void setPageChanged() {
		this.pageChanged = true;
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
	
	public int getLocalNumberComputer() {	
		switch(this.state) {
			case SEARCH:
				return localNumberComputer;
			case ORDERBYSEARCH:
				return localNumberComputer;
			default:
				return numberComputer;
		}
	}
	
	public static int getNumberPage() {		
		return numberPage;
	}
	
	public int getLocalNumberPage() {
		switch(this.state) {
		case SEARCH:
			return localNumberPage;
		case ORDERBYSEARCH:
			return localNumberPage;
		default:
			return numberPage;
		}
	}
	
	public int[] getButtonArray() {
		return buttonArray;
	}
	
	public State getState() {
		return state;
	}
	
	public void changeState(State state) {
		ComputerDAO computers = new ComputerDAO();
		this.state = state;
		if(state==State.SEARCH || state==State.ORDERBYSEARCH) {
			this.localNumberComputer = computers.getComputerCountSearch(searchedEntry);
		} else {
			this.localNumberComputer = computers.getComputerCount();
		}
		this.localNumberPage = (localNumberComputer/ComputerDAO.NUMBER_RESULT_BY_PAGE)+1;
		this.pageChanged = true;
	}
	
	public static void setNumberComputer(int newNumberComputer) {
		numberComputer = newNumberComputer;
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
	
	public void setLocalPageIndex(int index) {
		if(IndexValidator.localIndexValidator(this,index)) {
			this.pageIndex = index;
			this.pageChanged = true;
		}
	}


	public int getPageIndex() {
		return pageIndex;
	}
	
	public void setSearchedEntry(String searchedEntry) {
		this.searchedEntry = searchedEntry;
	}
	
	public String getSearchedEntry() {
		return searchedEntry;
	}

	public ArrayList<Computer> getComputerPageList() {
		if (pageChanged) {
			ComputerDAO computers = new ComputerDAO();
			switch(this.state) {
				case NORMAL:
					this.computerPageList = computers.getComputerRange(pageIndex);
					break;
				case SEARCH:
					this.computerPageList = computers.getSearchedComputerRange(pageIndex, searchedEntry);
					break;
				case ORDERBY:
					break;
				case ORDERBYSEARCH:
					break;
				}
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
		return pageIndex == 0;
	}
	
	public boolean testRight() {
		return pageIndex == numberPage-1;
	}
	
	public boolean testRightBlock() {
		return (pageIndex + NUMBER_BUTTON )>= numberPage;
	}
	
	public boolean testLeftBlock() {
		return pageIndex < NUMBER_BUTTON;
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


