package com.oxyl.controller;

import java.util.ArrayList;

import com.oxyl.dao.Companies;
import com.oxyl.model.Company;

public class CompanyPageHandler {
	private static CompanyPageHandler instance;
	private ArrayList<Company> companyPageList;
	private int pageIndex;
	private int numberPage;
	private Companies companies;
	
	private CompanyPageHandler(ArrayList<Company> companyPageList, int numberPage) {
		this.setCompanyPageList(companyPageList);
		this.pageIndex = 0;
		this.numberPage = numberPage/Companies.NUMBER_RESULT_BY_PAGE;
		this.companies = Companies.getInstance();
 	}
	
	
	public static CompanyPageHandler getInstance(ArrayList<Company> in, int numberPage) {
		if (instance == null) {
			instance = new CompanyPageHandler(in,numberPage);
		}
		return instance;
	}
	
	public static CompanyPageHandler getInstance() {
		if (instance == null) {
			System.out.println("There is no instance");
			System.out.println("Please use getInstance(ArrayList<Company> in, int numberPage)");
		}
		return instance;		
	}

	public int getPageIndex() {
		return pageIndex;
	}


	public ArrayList<Company> getCompanyPageList() {
		return companyPageList;
	}


	public void setCompanyPageList(ArrayList<Company> computerPageList) {
		this.companyPageList = computerPageList;
	}


	public int getNumberPage() {
		return numberPage;
	}	
	
	public void handlePage(int result) {
		updateInfo(result);
		
	}
	
	private void updateInfo(int entry) {
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
}
