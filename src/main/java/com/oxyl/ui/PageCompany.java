package com.oxyl.ui;
import com.oxyl.controller.CompanyPageHandler;
import com.oxyl.model.Company;

public class PageCompany {
	
	private static CompanyPageHandler companyPageHandler = CompanyPageHandler.getInstance();
	
	public void showPage() {
		System.out.println("id :   \tname :    ");
		for(Company company : companyPageHandler.getCompanyPageList()) {
			System.out.println(company);
		}
	}
	
	public static void controllerMessage() {
		if(companyPageHandler.testLeft()) {
			System.out.println("Entrez 2 pour voir la page de droite - Entrez 3 pour revenir au menu");
		} else if(companyPageHandler.testRight()) {
			System.out.println("Entrez 1 pour voir la page de gauche - Entrez 3 pour revenir au menu");
		} else {
			System.out.println("Entrez 1 pour voir la page de gauche - Entrez 2 pour voir la page de droite - Entrez 3 pour revenir au menu");
		}
	}
 }