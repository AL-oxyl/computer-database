package com.oxyl.service;

import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.oxyl.dao.ComputerDAO;
import com.oxyl.dto.ComputerDTO;
import com.oxyl.model.Computer;
import com.oxyl.ui.Pagination;

public class ComputerPageHandlerStrategyService implements GenericPageHandler<ComputerDTO>{
	
	private ArrayList<ComputerDTO> computerPageList;
	private int pageIndex;
	private int numberPage;
	private int numberComputer;
	private static final Logger LOGGER = LoggerFactory.getLogger(ComputerPageHandlerStrategyService.class);

	
	public ComputerPageHandlerStrategyService() {
		ComputerDAO computers = new ComputerDAO();
		this.computerPageList = computers.getComputerRange(0);
		this.pageIndex = 0;
		this.numberComputer = computers.getComputerCount();
		this.numberPage = (numberComputer/ComputerDAO.NUMBER_RESULT_BY_PAGE)+1;
	}
	
	public void setPageIndex(int index) {
		if(index < 0) {
			LOGGER.error("Index négatif interdit");
		} else if (index >= numberPage) {
			LOGGER.error("Index supérieur au nombre de page total");
		} else {
			this.pageIndex = index;
		}
	}

	public int getPageIndex() {
		return pageIndex;
	}


	public ArrayList<ComputerDTO> getComputerPageList() {
		return computerPageList;
	}


	public void setPageList(ArrayList<ComputerDTO> computerPageList) {
		this.computerPageList = computerPageList;
	}


	public int getNumberPage() {
		return numberPage;
	}	
	
	public int getNumberComputer() {
		return numberComputer;
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

	@Override
	public ArrayList<ComputerDTO> getPageList() {
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


