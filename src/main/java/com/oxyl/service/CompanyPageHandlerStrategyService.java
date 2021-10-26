package com.oxyl.service;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.oxyl.dao.CompanyDAO;
import com.oxyl.model.Company;
import com.oxyl.ui.Pagination;

@Service
@Scope("prototype")
public class CompanyPageHandlerStrategyService implements GenericPageHandler<Company>{
	private final int NUMBER_RESULT_BY_PAGE = 10;
	private int numberPage;
	private CompanyDAO companies;
	private int pageIndex;
	private List<Optional<Company>> companyPageList;
	private static final Logger LOGGER = LoggerFactory.getLogger(CompanyPageHandlerStrategyService.class);

	@Autowired
	public CompanyPageHandlerStrategyService(CompanyDAO companyDao) {
		this.companyPageList = companyDao.getCompanyRange(0,NUMBER_RESULT_BY_PAGE);
		this.pageIndex = 0;
		int numberResult = companyDao.getCompanyCount();
		this.numberPage = (numberResult/NUMBER_RESULT_BY_PAGE)+1;
 	}

	public int getPageIndex() {
		return pageIndex;
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


	public List<Optional<Company>> getPageList() {
		return companyPageList;
	}

	public int getNumberPage() {
		return numberPage;
	}	
	
	public void handlePage(int result) {
		switch (result) {
			case 1:
				updateInfo(pageIndex-1);
				break;
			case 2:
				updateInfo(pageIndex+1);
				break;
		}		
	}
	
	public void updateInfo(int entry) {
		int ref = pageIndex;
		setPageIndex(entry);
		if(ref != pageIndex) {
			this.companyPageList = companies.getCompanyRange(pageIndex,NUMBER_RESULT_BY_PAGE);
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
	public void setPageList(List<Optional<Company>> pageList) {
		this.companyPageList = pageList;
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
