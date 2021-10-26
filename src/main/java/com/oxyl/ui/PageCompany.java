package com.oxyl.ui;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;

import com.oxyl.model.Company;

@Component
public class PageCompany {
	
	List<Optional<Company>> currentCompanyListOnPage;
	
	public PageCompany(List<Optional<Company>> currentCompanyListOnPage) {
		this.currentCompanyListOnPage = currentCompanyListOnPage;
	}
		
	public void showPage() {
		System.out.println("id :   \tname :    ");
		for(Optional<Company> company : currentCompanyListOnPage) {
			if(company.isPresent()) {
				System.out.println(company.get());
			}
		}
	}
	
	public static void controllerMessage(Pagination pageText) {
		System.out.println(pageText.texte);
	}
	
	public void setCurrentCompanyListOnPage(List<Optional<Company>> currentCompanyListOnPage) {
		this.currentCompanyListOnPage = currentCompanyListOnPage;
	}
 }