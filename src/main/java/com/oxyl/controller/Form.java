package com.oxyl.controller;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Scanner;

import com.oxyl.dao.CompanyDAO;
import com.oxyl.dao.ComputerDAO;
import com.oxyl.model.Computer;
import com.oxyl.ui.ComputerInfo;
import com.oxyl.ui.Menu;
import com.oxyl.ui.PageCompany;
import com.oxyl.ui.PageComputer;

public class Form {
	
	private int firstMenuEntry;
	private int secondMenuEntry;
	private final HashSet<Integer> validFirstMenuValues = new HashSet<Integer>(Arrays.asList(1, 2, 3, 4, 5, 6, 7));
	private final HashSet<Integer> validSecondMenuValues = new HashSet<Integer>(Arrays.asList(1, 2, 3));
	private ComputerPageHandlerStrategy computerPageHandler;
	private CompanyPageHandlerStrategy companyPageHandler;
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
		ComputerDAO computers = new ComputerDAO();
		this.computerPageHandler = new ComputerPageHandlerStrategy(computers.getComputerRange(0),computers.getComputerCount());
		this.pageComputer = new PageComputer(computerPageHandler.getPageList());
		pageComputer.showPage();
	}
	
	private void initCompanyPage() {
		CompanyDAO companies = CompanyDAO.getInstance();
		this.companyPageHandler = new CompanyPageHandlerStrategy(companies.getCompanyRange(0),companies.getCompanyCount());
		this.pageCompany = new PageCompany(companyPageHandler.getPageList());
		pageCompany.showPage();
	}
	
	private void showComputerList() {
		initComputerPage();
		while(secondMenuEntry != 3) {
			PageComputer.controllerMessage(computerPageHandler.getPageState());
			getSecureComputerPaginationInput();
			computerPageHandler.handlePage(secondMenuEntry);
			pageComputer.setCurrentComputerListOnPage(computerPageHandler.getPageList());
			pageComputer.showPage();
		}
		this.secondMenuEntry = 0;
	}
	
	private void showCompanyList() {
		initCompanyPage();
		while(secondMenuEntry != 3) {
			PageCompany.controllerMessage(companyPageHandler.getPageState());
			getSecureCompaniesPaginationInput();
			companyPageHandler.handlePage(secondMenuEntry);
			pageCompany.setCurrentCompanyListOnPage(companyPageHandler.getPageList());
			pageCompany.showPage();
		}
		this.secondMenuEntry = 0;
	}
	
	private void showComputerInfo() {
		boolean validId = false;
		while(!validId) {
			System.out.print("Entrez l'id de l'ordinateur souhaité : ");
			Computer computer = getSecureComputerInfoInput();
			if (computer!=null) {
				ComputerInfo computerInfo = new ComputerInfo(computer);
				computerInfo.show();
				validId = true;
			} else {
				System.out.println("ID non valide. Merci de mettre une ID valide");
			}
		}
		System.out.println();
	}
	
	private void addNewComputerInfo() {
		boolean validId = false;
		while(!validId) {
			System.out.print("Bienvenue dans le menu de création d'ordinateur");
			Computer computer = getSecureComputerInfoInput();
			if (computer!=null) {
				ComputerInfo computerInfo = new ComputerInfo(computer);
				computerInfo.show();
				validId = true;
			} else {
				System.out.println("ID non valide. Merci de mettre une ID valide");
			}
		}
		System.out.println();
	}
	
	private void updateComputerInfo() {
		boolean validId = false;
		while(!validId) {
			System.out.print("Entrez l'id de l'ordinateur souhaité : ");
			Computer computer = getSecureComputerInfoInput();
			if (computer!=null) {
				ComputerInfo computerInfo = new ComputerInfo(computer);
				computerInfo.show();
				validId = true;
			} else {
				System.out.println("ID non valide. Merci de mettre une ID valide");
			}
		}
		System.out.println();
	}
	
	private void deleteComputerInfo() {
		boolean validId = false;
		while(!validId) {
			System.out.print("Entrez l'id de l'ordinateur souhaité : ");
			int id = getSecureComputerIdInput();
			ComputerDAO computers = new ComputerDAO();
			validId = true;
			if(computers.deleteComputer(id)) {
				System.out.println("L'ordinateur " + id + " a bien été supprimé.\n"); 
			} else {
				System.out.println("ID non valide. Aucun ordinateur n'a été supprimé");
			}
		}
	}
	
	public void menu() {
		while(continueMenu) {
			FormChoice choice = FormChoice.QUIT;
			Menu.showBootingMenu();
			getSecureMenuInput();
			for(FormChoice formChoice : FormChoice.values()) {
				if (formChoice.getId() == firstMenuEntry) {
					choice = formChoice;
					break;
				}
			}
			switch(choice) {
			case LISTCOMPUTER:
				showComputerList();
				break;
			case LISTCOMPANY:
				showCompanyList();
				break;
			case COMPUTERINFO:
				showComputerInfo();
				break;
			case CREATECOMPUTER:
				addNewComputerInfo();
				break;
			case UPDATECOMPUTER:
				updateComputerInfo();
				break;
			case DELETECOMPUTER:
				deleteComputerInfo();
				break;
			case QUIT:
				this.continueMenu = false;
				this.scan.close();
				break;
			}
		}
	}
	
	private int getSecureComputerIdInput() {
		boolean validEntry = false;
		while(!validEntry) {
			if (scan.hasNextInt()) {
				System.out.println("Entrée valide");
				return scan.nextInt();
			} else {
				System.out.println("\nCeci n'est pas un entier, merci de rentrer un entier\n");
			}
			scan.nextLine();
			System.out.print("Entrez l'id de l'ordinateur souhaité :");
		}
		System.out.println("Entry error");
		return 0;
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
	
	private void getSecureComputerPaginationInput() {
		boolean validEntry = false;
		while(!validEntry) {
			if (scan.hasNextInt()) {
				this.secondMenuEntry = scan.nextInt();
				if (!isOutOfBoundComputer()) {
					break;
				} else {
					System.out.println("\nCet entier n'est pas reconnu, merci de rentrer une valeur affiché sur le menu\n");
				}
			} else {
				System.out.println("\nCeci n'est pas un entier, merci de rentrer un entier\n");
			}
			scan.nextLine();
			PageComputer.controllerMessage(computerPageHandler.getPageState());
		}
		System.out.println("Entrée valide");
	} 
	
	private void getSecureCompaniesPaginationInput() {
		boolean validEntry = false;
		while(!validEntry) {
			if (scan.hasNextInt()) {
				this.secondMenuEntry = scan.nextInt();
				if (!isOutOfBoundCompany()) {
					break;
				} else {
					System.out.println("\nCet entier n'est pas reconnu, merci de rentrer une valeur affiché sur le menu\n");
				}
			} else {
				System.out.println("\nCeci n'est pas un entier, merci de rentrer un entier\n");
			}
			scan.nextLine();
			PageCompany.controllerMessage(companyPageHandler.getPageState());
		}
		System.out.println("Entrée valide");
	} 
	
	private Computer getSecureComputerInfoInput() {
		boolean validEntry = false;
		int value;
		while(!validEntry) {
			if (scan.hasNextInt()) {
				value = scan.nextInt();
				try {
					ComputerDAO computers = new ComputerDAO();
					Computer computer = computers.getComputer(value);	
					return computer;
				} catch (SQLException e){
					System.out.println("ID non valide, merci de réessayer");
				}	
			} else {
				System.out.println("\nCeci n'est pas un entier, merci de rentrer un entier\n");
			}
			scan.nextLine();
			System.out.print("Entrez l'id de l'ordinateur souhaité :");
		}
		return null;
	}
	
	private boolean isOutOfBoundCompany() {
		if (validSecondMenuValues.contains(secondMenuEntry) && 
		   (secondMenuEntry != 1 || !companyPageHandler.testLeft()) &&
		   (secondMenuEntry != 2 || !companyPageHandler.testRight())) {
			return false;
		}
		return true;
	}
	
	private boolean isOutOfBoundComputer() {
		if (validSecondMenuValues.contains(secondMenuEntry) && 
		   (secondMenuEntry != 1 || !computerPageHandler.testLeft()) &&
		   (secondMenuEntry != 2 || !computerPageHandler.testRight())) {
			return false;
		}
		return true;
	}
}
