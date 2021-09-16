package com.oxyl.controller;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Scanner;

import com.oxyl.dao.Companies;
import com.oxyl.dao.Computers;
import com.oxyl.ui.Menu;
import com.oxyl.ui.PageCompany;
import com.oxyl.ui.PageComputer;

public class Form {
	
	private int firstMenuEntry;
	private int secondMenuEntry;
	private final HashSet<Integer> validFirstMenuValues = new HashSet<Integer>(Arrays.asList(1, 2, 3, 4, 5, 6, 7));
	private final HashSet<Integer> validSecondMenuValues = new HashSet<Integer>(Arrays.asList(1, 2, 3));
	private ComputerPageHandler computerPageHandler;
	private CompanyPageHandler companyPageHandler;
	private Scanner scan;
	private boolean continueMenu;
	PageComputer pageComputer;
	PageCompany pageCompany;
	
	public Form() {
		this.scan = new Scanner(System.in);
		this.secondMenuEntry = 0;
		this.continueMenu = true;
	}

	
	private void initComputerPage() {
		Computers computers = new Computers();
		this.computerPageHandler = ComputerPageHandler.getInstance(computers.getComputerRange(0),computers.getComputerCount());
		this.pageComputer = new PageComputer();
		pageComputer.showPage();
	}
	
	private void initCompanyPage() {
		Companies companies = Companies.getInstance();
		this.companyPageHandler = CompanyPageHandler.getInstance(companies.getCompanyRange(0),companies.getCompanyCount());
		this.pageCompany = new PageCompany();
		pageCompany.showPage();
	}
	
	public void menu() {
		while(continueMenu) {
			Menu.showBootingMenu();
			getSecureMenuInput();
			switch(firstMenuEntry) {
			case 1:
				initComputerPage();
				while(secondMenuEntry != 3) {
					PageComputer.controllerMessage();
					getSecureComputerPaginationInput();
					computerPageHandler.handlePage(secondMenuEntry);
					pageComputer.showPage();
				}
				this.secondMenuEntry = 0;
				break;
			case 2:
				initCompanyPage();
				while(secondMenuEntry != 3) {
					PageCompany.controllerMessage();
					getSecureCompaniesPaginationInput();
					companyPageHandler.handlePage(secondMenuEntry);
					pageCompany.showPage();
				}
				this.secondMenuEntry = 0;
				break;
			case 3:
				
			case 4:
				
			case 5:
				
			case 6:
				
			case 7:
				this.continueMenu = false;
				this.scan.close();
				break;
			}
		}
	}
	
	private void getSecureMenuInput() {
		boolean validEntry = false;
		while(!validEntry) {
			if (scan.hasNextInt()) {
				this.firstMenuEntry = scan.nextInt();
				if (validFirstMenuValues.contains(firstMenuEntry)) {
					break;
				} else {
					System.out.println("\nCet entier n'est pas reconnu, merci de rentrer une valeur affiché sur le menu\n");
				}
			} else {
				System.out.println("\nCeci n'est pas un entier, merci de rentrer un entier\n");
			}
			scan.nextLine();
			Menu.showBootingMenu();
		}
		System.out.println("Entrée valide");
	}
	
	public void getSecureComputerPaginationInput() {
		boolean validEntry = false;
		while(!validEntry) {
			if (scan.hasNextInt()) {
				this.secondMenuEntry = scan.nextInt();
				if (validSecondMenuValues.contains(secondMenuEntry) && 
				   (secondMenuEntry != 1 || !computerPageHandler.testLeft()) &&
				   (secondMenuEntry != 2 || !computerPageHandler.testRight())) {
					break;
				} else {
					System.out.println("\nCet entier n'est pas reconnu, merci de rentrer une valeur affiché sur le menu\n");
				}
			} else {
				System.out.println("\nCeci n'est pas un entier, merci de rentrer un entier\n");
			}
			scan.nextLine();
			PageComputer.controllerMessage();
		}
		System.out.println("Entrée valide");
	} 
	
	public void getSecureCompaniesPaginationInput() {
		boolean validEntry = false;
		while(!validEntry) {
			if (scan.hasNextInt()) {
				this.secondMenuEntry = scan.nextInt();
				if (validSecondMenuValues.contains(secondMenuEntry) && 
				   (secondMenuEntry != 1 || !companyPageHandler.testLeft()) &&
				   (secondMenuEntry != 2 || !companyPageHandler.testRight())) {
					break;
				} else {
					System.out.println("\nCet entier n'est pas reconnu, merci de rentrer une valeur affiché sur le menu\n");
				}
			} else {
				System.out.println("\nCeci n'est pas un entier, merci de rentrer un entier\n");
			}
			scan.nextLine();
			PageCompany.controllerMessage();
		}
		System.out.println("Entrée valide");
	} 
	
	
}
