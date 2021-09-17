package com.oxyl.ui;
import java.util.ArrayList;

import com.oxyl.model.Computer;

public class PageComputer {
	
	ArrayList<Computer> currentComputerListOnPage;
	
	public PageComputer(ArrayList<Computer> currentComputerListOnPage) {
		this.currentComputerListOnPage = currentComputerListOnPage;
	}
	
	public void showPage() {
		System.out.println("id    \tname :    \tintroduction date :   \tdiscontinuedDate :   \tcompany : ");
		for(Computer computer : currentComputerListOnPage) {
			System.out.println(computer);
		}	
	}
	
	public static void controllerMessage(Pagination pageText) {
		System.out.println(pageText.texte);
	}
	
	public void setCurrentComputerListOnPage(ArrayList<Computer> currentComputerListOnPage) {
		this.currentComputerListOnPage = currentComputerListOnPage;
	}
 }
