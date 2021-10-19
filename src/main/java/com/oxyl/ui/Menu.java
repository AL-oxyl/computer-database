package com.oxyl.ui;

public class Menu {
	private static final String newLine = System.getProperty("line.separator");
	
	public static void showBootingMenu() {
		StringBuilder menu = new StringBuilder();
		menu.append("Bienvenue dans le menu d'interraction à la base de donnée:") .append(newLine)
	        .append("Entrez un entier pour pouvoir interragir").append(newLine)		    
			.append("1- Pour afficher tout les ordinateurs disponibles ").append(newLine)			
			.append("2- Pour afficher toutes les entreprises disponibles ").append(newLine)			
			.append("3- Pour afficher les détails d'un ordinateur").append(newLine)			
			.append("4- Pour créer un ordinateur").append(newLine)			
			.append("5- Pour mettre a jour un ordinateur").append(newLine)			
			.append("6- Pour supprimer un ordinateur").append(newLine)			
			.append("7- Pour quitter").append(newLine)			
			.append("Toute erreur de saisie réaffichera ce menu...");
		System.out.println(menu);
	}
}
