package com.oxyl.dto;

import java.time.LocalDateTime;
import java.util.Optional;

import com.oxyl.model.Company;
import com.oxyl.model.Computer;



public class ComputerDTO {
	
	private final String computerName;
	private final String introductionDate;
	private final String discontinuedDate;
	private final Optional<String> manufacturerName;
	private final Optional<String> manufacturerId;
	
	public ComputerDTO(Computer computer) {
		Optional<String> manufacturerId = Optional.empty();
		this.computerName = computer.getComputerName();
		Optional<LocalDateTime> introductionDate = computer.getIntroductionDate();
		Optional<LocalDateTime> discontinuedDate = computer.getDiscontinuedDate();
		this.introductionDate = introductionDate.map(LocalDateTime::toString).orElse("");
		this.discontinuedDate = discontinuedDate.map(LocalDateTime::toString).orElse("");
		Company manufacturer = computer.getManufacturer().orElse(new Company());
		this.manufacturerName = Optional.of(manufacturer.getName());
		if (manufacturerName.get() != "") {
			manufacturerId = Optional.of(manufacturer.getName());
		} 
		this.manufacturerId = manufacturerId;
	}
	
	public ComputerDTO(String computerName, String introductionDate, String discontinuedDate, String manufacturerName, String manufacturerId) {
		this.computerName = computerName;
		this.introductionDate = introductionDate;
		this.discontinuedDate = discontinuedDate;
		this.manufacturerName = Optional.ofNullable(manufacturerName);
		this.manufacturerId = Optional.ofNullable(manufacturerId);
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

	public Optional<String> getManufacturer() {
		return manufacturerName;
	}
	
	public Optional<String> getId() {
		return manufacturerId;
	}
}
