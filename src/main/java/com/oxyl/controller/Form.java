package com.oxyl.controller;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Scanner;

import com.oxyl.dao.Computers;
import com.oxyl.ui.Menu;
import com.oxyl.ui.PageComputer;

public class Form {
	
	private int menuEntry;
	private final HashSet<Integer> validValues = new HashSet<Integer>(Arrays.asList(1, 2, 3, 4, 5, 6));
	
	public Form(FormType form) {
		switch(form.getIndex()) {
		case 1:
			initMenu();
			break;
		case 2:
			initPage();
			break;
		default:
			System.out.println("Pas de formulaire enregistré");
		}
	}
	
	private void initPage() {
		
	}
	
	private void initMenu() {
		Menu.showBootingMenu();
		getSecureMenuInput();
		switch(this.menuEntry) {
		case 1:
			Computers computers = new Computers();
			ComputerPageHandler computerPageHandler = ComputerPageHandler.getInstance(computers.getComputerRange(0),computers.getComputerCount());
			PageComputer firstPage = new PageComputer(computerPageHandler.getComputerPageList());
			firstPage.showPage();
			break;
		case 2:
			
		case 3:
			
		case 4:
			
		case 5:
			
		case 6:

		}
	}
	
	private void getSecureMenuInput() {
		boolean validEntry = false;
		Scanner scan = new Scanner(System.in);
		while(!validEntry) {
			if (scan.hasNextInt()) {
				this.menuEntry = scan.nextInt();
				if (validValues.contains(menuEntry)) {
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
		scan.close();
		System.out.println("\nEntrée valide");
	}
	
	
}
