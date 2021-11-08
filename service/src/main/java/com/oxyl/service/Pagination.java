package com.oxyl.service;

public enum Pagination {
	LEFT("Entrez 2 pour voir la page de droite - Entrez 3 pour revenir au menu"),
	RIGHT("Entrez 1 pour voir la page de gauche - Entrez 3 pour revenir au menu"),
	MIDDLE("Entrez 1 pour voir la page de gauche - Entrez 2 pour voir la page de droite - Entrez 3 pour revenir au menu");
	
	public String texte;
	
	private Pagination(String texte) {
		this.texte = texte;
	}
}
