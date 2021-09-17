package com.oxyl.controller;

import java.util.ArrayList;

import com.oxyl.dao.CompanyDAO;
import com.oxyl.model.Company;
import com.oxyl.ui.Pagination;

public class CompanyPageHandlerStrategy implements GenericPageHandler<Company>{
	private int numberPage;
	private CompanyDAO companies;
	private int pageIndex;
	private ArrayList<Company> companyPageList;
	
	public CompanyPageHandlerStrategy(ArrayList<Company> pageList, int numberPage) {
		this.companyPageList = pageList;
		this.pageIndex = 0;
		this.numberPage = numberPage/CompanyDAO.NUMBER_RESULT_BY_PAGE;
		this.companies = CompanyDAO.getInstance();
 	}

	public int getPageIndex() {
		return pageIndex;
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
		switch (entry) {
			case 1:
				pageIndex--;
				break;
			case 2:
				pageIndex++;
				break;
		}	
		this.companyPageList = companies.getCompanyRange(pageIndex);
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
