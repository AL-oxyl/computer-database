package com.oxyl.ui;
import java.util.ArrayList;

import com.oxyl.dto.ComputerDTO;

public class PageComputer {
	
	ArrayList<ComputerDTO> currentComputerListOnPage;
	
	public PageComputer(ArrayList<ComputerDTO> currentComputerListOnPage) {
		this.currentComputerListOnPage = currentComputerListOnPage;
	}
	
	public void showPage() {
		System.out.println("id    \tname :    \tintroduction date :   \tdiscontinuedDate :   \tcompany : ");
		for(ComputerDTO computer : currentComputerListOnPage) {
			System.out.println(computer);
		}	
	}
	
	public static void controllerMessage(Pagination pageText) {
		System.out.println(pageText.texte);
	}
	
	public void setCurrentComputerListOnPage(ArrayList<ComputerDTO> currentComputerListOnPage) {
		this.currentComputerListOnPage = currentComputerListOnPage;
	}
 }
