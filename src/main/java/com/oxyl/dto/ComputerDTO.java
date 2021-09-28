package com.oxyl.dto;

import com.oxyl.model.Company;
import com.oxyl.model.Computer;



public class ComputerDTO {
	
	private final String computerName;
	private final String introductionDate;
	private final String discontinuedDate;
	private final String manufacturer;
	
	public ComputerDTO(Computer computer) {
		this.computerName = computer.getComputerName();
		if(computer.getIntroductionDate()!=null) {
			this.introductionDate = computer.getIntroductionDate().toString();
		} else {
			this.introductionDate = "";
		}
		if(computer.getDiscontinuedDate()!=null) {
			this.discontinuedDate = computer.getDiscontinuedDate().toString();
		} else {
			this.discontinuedDate = "";
		}
		Company manufacturer = computer.getManufacturer().orElse(new Company());
		this.manufacturer = manufacturer.getName();
	}

	public String getComputerName() {
		return computerName;
	}

	public String getIntroductionDate() {
		return introductionDate;
	}

	public String getDiscontinuedDate() {
		return discontinuedDate;
	}

	public String getManufacturer() {
		return manufacturer;
	}
}
