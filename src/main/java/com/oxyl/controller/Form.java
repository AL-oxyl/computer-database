package com.oxyl.controller;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Scanner;

import com.oxyl.ui.Menu;

public class Form {
	
	private int menuEntry;
	private boolean validEntry;
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
		getSecureIntInput();
	}
	
	private void getSecureIntInput() {
		Scanner scan = new Scanner(System.in);
		while(!this.validEntry) {
			if (scan.hasNextInt()) {
				this.menuEntry = scan.nextInt();
				if (validValues.contains(menuEntry)) {
					this.validEntry = true;
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
