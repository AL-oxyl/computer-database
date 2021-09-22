package com.oxyl.service;

import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.oxyl.controller.GenericPageHandler;
import com.oxyl.dao.CompanyDAO;
import com.oxyl.model.Company;
import com.oxyl.ui.Pagination;

public class CompanyPageHandlerStrategyService implements GenericPageHandler<Company>{
	private int numberPage;
	private CompanyDAO companies;
	private int pageIndex;
	private ArrayList<Company> companyPageList;
	private static final Logger LOGGER = LoggerFactory.getLogger(CompanyPageHandlerStrategyService.class);

	
	public CompanyPageHandlerStrategyService() {
		this.companies = CompanyDAO.getInstance();
		this.companyPageList = companies.getCompanyRange(0);
		this.pageIndex = 0;
		int numberResult = companies.getCompanyCount();
		this.numberPage = (numberResult/CompanyDAO.NUMBER_RESULT_BY_PAGE)+1;
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


	public ArrayList<Company> getPageList() {
		return companyPageList;
	}

	public int getNumberPage() {
		return numberPage;
	}	
	
	public void handlePage(int result) {
		updateInfo(result);
		
	}
	
	public void updateInfo(int entry) {
		int ref = pageIndex;
		switch (entry) {
			case 1:
				setPageIndex(pageIndex-1);
				break;
			case 2:
				setPageIndex(pageIndex+1);
				break;
		}	
		if(ref != pageIndex) {
			this.companyPageList = companies.getCompanyRange(pageIndex);
			LOGGER.info("Company page info updated");
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
	public void setPageList(ArrayList<Company> pageList) {
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
