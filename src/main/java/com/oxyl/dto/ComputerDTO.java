package com.oxyl.dto;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

import com.oxyl.model.Company;
import com.oxyl.model.Computer;



public class ComputerDTO {
	private final String computerId;
	private final String computerName;
	private final String introductionDate;
	private final String discontinuedDate;
	private final String manufacturerName;
	private final Optional<String> manufacturerId;
	
	public ComputerDTO() {
		this.computerId = "";
		this.computerName = "";
		this.introductionDate = "";
		this.discontinuedDate = "";
		this.manufacturerName = "";
		this.manufacturerId = Optional.of("");
	}
	
	public ComputerDTO(Computer computer) {
		Optional<String> manufacturerId = Optional.empty();
		this.computerName = computer.getComputerName();
		this.computerId = Integer.toString(computer.getId()).toString();
		Optional<LocalDate> introductionDate = computer.getIntroductionDate();
		Optional<LocalDate> discontinuedDate = computer.getDiscontinuedDate();
		this.introductionDate = introductionDate.map(date->date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))).orElse("");
		this.discontinuedDate = discontinuedDate.map(date->date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))).orElse("");
		Company manufacturer = computer.getManufacturer().orElse(new Company());
		this.manufacturerName = manufacturer.getName();
		if(!"".equals(manufacturer.getName())) {
			this.manufacturerId = Optional.of(Integer.toString(manufacturer.getId()));
		} else {
			this.manufacturerId = manufacturerId;
		}
	}
	
	public ComputerDTO(String computerId, String computerName, String introductionDate, String discontinuedDate, String manufacturerName, String manufacturerId) {
		this.computerId = computerId;
		this.computerName = computerName;
		this.introductionDate = introductionDate;
		this.discontinuedDate = discontinuedDate;
		this.manufacturerName = manufacturerName;
		this.manufacturerId = Optional.ofNullable(manufacturerId);
	}
	
	public String getComputerId() {
		return computerId;
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
		return manufacturerName;
	}
	
	public Optional<String> getCompanyId() {
		return manufacturerId;
	}
}
