package com.oxyl.ui;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;

import com.oxyl.dto.ComputerDTO;
import com.oxyl.mapper.ComputerMapper;
import com.oxyl.model.Computer;

@Component
public class PageComputer {
	
	List<ComputerDTO> currentComputerListOnPage;
	
	public PageComputer(List<Optional<Computer>> currentComputerListOnPage) {	
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
	
	public void setCurrentComputerListOnPage(List<Optional<Computer>> currentComputerListOnPage) {
		this.currentComputerListOnPage = ComputerMapper.computerListToDTOList(currentComputerListOnPage);
	}
 }
