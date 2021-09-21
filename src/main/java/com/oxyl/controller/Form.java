package com.oxyl.controller;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Scanner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.oxyl.dao.ComputerDAO;
import com.oxyl.model.Computer;
import com.oxyl.service.CompanyPageHandlerStrategyService;
import com.oxyl.service.ComputerPageHandlerStrategyService;
import com.oxyl.ui.ComputerInfo;
import com.oxyl.ui.Menu;
import com.oxyl.ui.PageCompany;
import com.oxyl.ui.PageComputer;

public class Form {
	
	private int firstMenuEntry;
	private int secondMenuEntry;
	private final HashSet<Integer> validFirstMenuValues = new HashSet<Integer>(Arrays.asList(1, 2, 3, 4, 5, 6, 7));
	private final HashSet<Integer> validSecondMenuValues = new HashSet<Integer>(Arrays.asList(1, 2, 3));
	private ComputerPageHandlerStrategyService computerPageHandler;
	private CompanyPageHandlerStrategyService companyPageHandler;
	private Scanner scan;
	private boolean continueMenu;
	PageComputer pageComputer;
	PageCompany pageCompany;
	private static final Logger LOGGER = LoggerFactory.getLogger(Form.class);

	
	public Form() {
		this.scan = new Scanner(System.in);
		this.secondMenuEntry = 0;
		this.continueMenu = true;
	}
	
	private void initComputerPage() {
		this.computerPageHandler = new ComputerPageHandlerStrategyService();
		this.pageComputer = new PageComputer(computerPageHandler.getPageList());
		pageComputer.showPage();
	}
	
	private void initCompanyPage() {
		this.companyPageHandler = new CompanyPageHandlerStrategyService();
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
				LOGGER.error("ID non valide. Merci de mettre une ID valide");
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
				LOGGER.error("ID non valide. Merci de mettre une ID valide");
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
				LOGGER.error("ID non valide. Merci de mettre une ID valide");
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
				LOGGER.info("L'ordinateur " + id + " a bien été supprimé.\n"); 
			} else {
				LOGGER.error("ID non valide. Aucun ordinateur n'a été supprimé");
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
				LOGGER.info("Entrée valide");
				return scan.nextInt();
			} else {
				LOGGER.error("\nCeci n'est pas un entier, merci de rentrer un entier\n");
			}
			scan.nextLine();
			System.out.print("Entrez l'id de l'ordinateur souhaité :");
		}
		LOGGER.debug("Entry error at getSecureComputerIdInput in Form.class");
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
					LOGGER.error("\nCet entier n'est pas reconnu, merci de rentrer une valeur affiché sur le menu\n");
				}
			} else {
				LOGGER.error("\nCeci n'est pas un entier, merci de rentrer un entier\n");
			}
			scan.nextLine();
			Menu.showBootingMenu();
		}
		LOGGER.info("Entrée valide");
	}
	
	private void getSecureComputerPaginationInput() {
		boolean validEntry = false;
		while(!validEntry) {
			if (scan.hasNextInt()) {
				this.secondMenuEntry = scan.nextInt();
				if (!isOutOfBoundComputer()) {
					break;
				} else {
					LOGGER.error("\nCet entier n'est pas reconnu, merci de rentrer une valeur affiché sur le menu\n");
				}
			} else {
				LOGGER.error("\nCeci n'est pas un entier, merci de rentrer un entier\n");
			}
			scan.nextLine();
			PageComputer.controllerMessage(computerPageHandler.getPageState());
		}
		LOGGER.info("Entrée valide");
	} 
	
	private void getSecureCompaniesPaginationInput() {
		boolean validEntry = false;
		while(!validEntry) {
			if (scan.hasNextInt()) {
				this.secondMenuEntry = scan.nextInt();
				if (!isOutOfBoundCompany()) {
					break;
				} else {
					LOGGER.error("\nCet entier n'est pas reconnu, merci de rentrer une valeur affiché sur le menu\n");
				}
			} else {
				LOGGER.error("\nCeci n'est pas un entier, merci de rentrer un entier\n");
			}
			scan.nextLine();
			PageCompany.controllerMessage(companyPageHandler.getPageState());
		}
		LOGGER.info("Entrée valide");
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
					LOGGER.error("ID non valide, merci de réessayer");
				}	
			} else {
				LOGGER.error("\nCeci n'est pas un entier, merci de rentrer un entier\n");
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
