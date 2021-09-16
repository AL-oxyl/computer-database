package com.oxyl.ui;

public class Menu {
	private static final String newLine = System.getProperty("line.separator");
	
	public static void showBootingMenu() {
		StringBuilder menu = new StringBuilder();
		menu.append("Bienvenue dans le menu d'interraction à la base de donnée:");
		menu.append(newLine);
		menu.append("Entrez un entier pour pouvoir interragir");
		menu.append(newLine);
		menu.append("1- Pour afficher tout les ordinateurs disponibles ");
		menu.append(newLine);
		menu.append("2- Pour afficher toutes les entreprises disponibles ");
		menu.append(newLine);
		menu.append("3- Pour afficher les détails d'un ordinateur");
		menu.append(newLine);
		menu.append("4- Pour créer un ordinateur");
		menu.append(newLine);
		menu.append("5- Pour mettre a jour un ordinateur");
		menu.append(newLine);
		menu.append("6- Pour supprimer un ordinateur");
		menu.append(newLine);
		menu.append("7- Pour quitter");
		menu.append(newLine);
		menu.append("Toute erreur de saisie réaffichera ce menu...");
		System.out.println(menu);
	}
	
	
}
