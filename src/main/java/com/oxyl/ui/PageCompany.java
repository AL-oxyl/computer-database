package com.oxyl.ui;
import java.util.ArrayList;

import com.oxyl.controller.CompanyPageHandler;
import com.oxyl.model.Company;

public class PageCompany {
	
	CompanyPageHandler companyPageHandler;
	
	ArrayList<Company> page;
	public PageCompany(ArrayList<Company> page) {
		this.page = page;
	}
	
	public void showPage() {
		System.out.println("id    \tname :    ");
		for(Company company : page) {
			System.out.println(company);
		}
	}
 }