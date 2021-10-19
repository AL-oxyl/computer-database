package com.oxyl.ui;
import java.util.ArrayList;

import org.springframework.stereotype.Component;

import com.oxyl.dto.ComputerDTO;
import com.oxyl.mapper.ComputerMapper;
import com.oxyl.model.Computer;

@Component
public class PageComputer {
	
	ArrayList<ComputerDTO> currentComputerListOnPage;
	
	public PageComputer(ArrayList<Computer> currentComputerListOnPage) {	
		this.currentComputerListOnPage = ComputerMapper.computerListToDTOList(currentComputerListOnPage);
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
	
	public void setCurrentComputerListOnPage(ArrayList<Computer> currentComputerListOnPage) {
		this.currentComputerListOnPage = ComputerMapper.computerListToDTOList(currentComputerListOnPage);
	}
 }
