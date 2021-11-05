package com.oxyl.ui;
import java.util.List;

import org.springframework.stereotype.Component;

import com.oxyl.model.Company;

@Component
public class PageCompany {
	
	List<Company> currentCompanyListOnPage;
	
	public PageCompany(List<Company> currentCompanyListOnPage) {
		this.currentCompanyListOnPage = currentCompanyListOnPage;
	}
		
	public void showPage() {
		System.out.println("id :   \tname :    ");
		for(Company company : currentCompanyListOnPage) {
			System.out.println(company);	
		}
	}
	
	public static void controllerMessage(Pagination pageText) {
		System.out.println(pageText.texte);
	}
	
	public void setCurrentCompanyListOnPage(List<Company> currentCompanyListOnPage) {
		this.currentCompanyListOnPage = currentCompanyListOnPage;
	}
 }