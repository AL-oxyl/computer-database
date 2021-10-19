package com.oxyl.ui;
import java.util.ArrayList;

import org.springframework.stereotype.Component;

import com.oxyl.model.Company;

@Component
public class PageCompany {
	
	ArrayList<Company> currentCompanyListOnPage;
	
	public PageCompany(ArrayList<Company> currentCompanyListOnPage) {
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
	
	public void setCurrentCompanyListOnPage(ArrayList<Company> currentCompanyListOnPage) {
		this.currentCompanyListOnPage = currentCompanyListOnPage;
	}
 }