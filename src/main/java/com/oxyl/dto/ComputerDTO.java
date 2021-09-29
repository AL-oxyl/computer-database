package com.oxyl.dto;

import java.sql.Date;
import java.util.Optional;

import com.oxyl.model.Company;
import com.oxyl.model.Computer;



public class ComputerDTO {
	
	private final String computerName;
	private final String introductionDate;
	private final String discontinuedDate;
	private final String manufacturer;
	
	public ComputerDTO(Computer computer) {
		this.computerName = computer.getComputerName();
		Optional<Date> introductionDate = computer.getIntroductionDate();
		Optional<Date> discontinuedDate = computer.getDiscontinuedDate();
		this.introductionDate = introductionDate.map(Date::toString).orElse("");
		this.discontinuedDate = discontinuedDate.map(Date::toString).orElse("");
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
