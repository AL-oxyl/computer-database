package com.oxyl.ui;
import com.oxyl.controller.ComputerPageHandler;
import com.oxyl.model.Computer;

public class PageComputer {
	
	private static ComputerPageHandler computerPageHandler = ComputerPageHandler.getInstance();
	
	public void showPage() {
		System.out.println("id    \tname :    \tintroduction date :   \tdiscontinuedDate :   \tcompany : ");
		for(Computer computer : computerPageHandler.getComputerPageList()) {
			System.out.println(computer);
		}	
	}
	
	public static void controllerMessage() {
		if(computerPageHandler.testLeft()) {
			System.out.println("Entrez 2 pour voir la page de droite - Entrez 3 pour revenir au menu");
		} else if(computerPageHandler.testRight()) {
			System.out.println("Entrez 1 pour voir la page de gauche - Entrez 3 pour revenir au menu");
		} else {
			System.out.println("Entrez 1 pour voir la page de gauche - Entrez 2 pour voir la page de droite - Entrez 3 pour revenir au menu");
		}
	}
 }
