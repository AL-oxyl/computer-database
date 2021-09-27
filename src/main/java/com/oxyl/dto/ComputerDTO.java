package com.oxyl.dto;

import java.sql.Date;

import com.oxyl.model.Company;
import com.oxyl.model.Computer;



public class ComputerDTO {
	
	private final String computerName;
	private final Date introductionDate;
	private final Date discontinuedDate;
	private final String manufacturer;
	
	public ComputerDTO(Computer computer) {
		this.computerName = computer.getComputerName();
		this.introductionDate = computer.getIntroductionDate();
		this.discontinuedDate = computer.getDiscontinuedDate();
		Company manufacturer = computer.getManufacturer().orElse(new Company());
		this.manufacturer = manufacturer.getName();
	}

	public String getComputerName() {
		return computerName;
	}

	public Date getIntroductionDate() {
		return introductionDate;
	}

	public Date getDiscontinuedDate() {
		return discontinuedDate;
	}

	public String getManufacturer() {
		return manufacturer;
	}
}
