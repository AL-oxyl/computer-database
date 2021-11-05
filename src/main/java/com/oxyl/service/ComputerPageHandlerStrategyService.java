package com.oxyl.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.oxyl.dao.ComputerDAO;
import com.oxyl.mapper.BddMapper;
import com.oxyl.model.Computer;
import com.oxyl.ui.Pagination;
import com.oxyl.validator.IndexValidator;

@Service
@Scope("prototype")
public class ComputerPageHandlerStrategyService implements GenericPageHandler<Computer>{
		
	private List<Computer> computerPageList;
	private Long pageIndex;
	private Long numberComputer;
	private Long numberPage;
	private static final int NUMBER_BUTTON = 5;
	private static final Logger LOGGER = LoggerFactory.getLogger(ComputerPageHandlerStrategyService.class);
	private Long localNumberComputer;
	private Long localNumberPage;
	private boolean pageChanged;
	private Long[] buttonArray = new Long[]{1L,2L,3L,4L,5L};
	private State state;
	private String searchedEntry = "";
	private ComputerDAO computers;
	private IndexValidator indexValidateur;
	private final Long NUMBER_RESULT_BY_PAGE = 10L;
	
	public ComputerPageHandlerStrategyService(ComputerDAO computerDao, IndexValidator indexValidateur ) {
		this.computers = computerDao;	
		this.indexValidateur = indexValidateur;
		numberComputer = initNumberComputer();
		numberPage = initNumberPage();
		this.computerPageList = BddMapper.computerPersistDtoListToComputerModelList(
				computers.getComputerRange(0L, NUMBER_RESULT_BY_PAGE));
		this.state = State.NORMAL;
		this.pageIndex = 0L;
	}
	
	public void setPageChanged() {
		this.pageChanged = true;
	}
	
	private Long initNumberComputer() {
		return computers.getComputerCount();
	}
	
	private Long initNumberPage() {
		return (numberComputer/NUMBER_RESULT_BY_PAGE)+1;
	}
	
	public Long getNumberComputer() {	
		return numberComputer;
	}
	
	public Long getLocalNumberComputer() {	
		switch(this.state) {
			case SEARCH:
				return localNumberComputer;
			case ORDERBYSEARCH:
				return localNumberComputer;
			default:
				return numberComputer;
		}
	}
	
	public Long getNumberPage() {		
		return numberPage;
	}
	
	public Long getLocalNumberPage() {
		switch(this.state) {
		case SEARCH:
			return localNumberPage;
		case ORDERBYSEARCH:
			return localNumberPage;
		default:
			return numberPage;
		}
	}
	
	public Long[] getButtonArray() {
		return buttonArray;
	}
	
	public State getState() {
		return state;
	}
	
	public void changeState(State state) {
		this.state = state;
		if(state==State.SEARCH || state==State.ORDERBYSEARCH) {
			this.localNumberComputer = computers.getComputerCountSearch(searchedEntry);
		} else {
			this.localNumberComputer = computers.getComputerCount();
		}
		this.localNumberPage = (localNumberComputer/NUMBER_RESULT_BY_PAGE)+1;
		this.pageChanged = true;
	}
	
	public void setNumberComputer(Long newNumberComputer) {
		numberComputer = newNumberComputer;
	}
	
	public void updateButtonArray(Long lastPage) {
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
		for(int i= NUMBER_BUTTON - 1; i>=0;i--) {
			buttonArray[i] = pageIndex + i - (NUMBER_BUTTON-1) + 1;
		}
	}
	
	
	public void setPageIndex(Long index) {
		if(indexValidateur.indexValidator(index,numberPage)) {
			this.pageIndex = index;
			this.pageChanged = true;
		}
	}
	
	public void setLocalPageIndex(Long index) {
		if(indexValidateur.indexValidator(index,localNumberPage)) {
			this.pageIndex = index;
			this.pageChanged = true;
		}
	}


	public Long getPageIndex() {
		return pageIndex;
	}
	
	public void setSearchedEntry(String searchedEntry) {
		this.searchedEntry = searchedEntry;
	}
	
	public String getSearchedEntry() {
		return searchedEntry;
	}

	public List<Computer> getComputerPageList() {
		if (pageChanged) {
			switch(this.state) {
				case NORMAL:
					this.computerPageList = BddMapper.computerPersistDtoListToComputerModelList(
							computers.getComputerRange(pageIndex, NUMBER_RESULT_BY_PAGE));
					break;
				case SEARCH:
					this.computerPageList = BddMapper.computerPersistDtoListToComputerModelList(
							computers.getSearchedComputerRange(pageIndex, NUMBER_RESULT_BY_PAGE, searchedEntry));
					break;
				case ORDERBY:
					break;
				case ORDERBYSEARCH:
					break;
				}
		}
		return computerPageList;
	}


	public void setPageList(List<Computer> computerPageList) {
		this.computerPageList = computerPageList;
	}	
	
	public void handlePage(int result) {
		switch (result) {
			case 1:
				updateInfo(Integer.toUnsignedLong(result-1));
				break;
			case 2:
				updateInfo(Integer.toUnsignedLong(result+1));
				break;
		}		
	}
	
	public void updateInfo(Long entry) {
		Long ref = pageIndex;
		setPageIndex(entry);
		if(ref != pageIndex) {
			this.computerPageList = BddMapper.computerPersistDtoListToComputerModelList(
					computers.getComputerRange(pageIndex, NUMBER_RESULT_BY_PAGE));
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
	public List<Computer> getPageList() {
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


