package com.oxyl.dto;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.oxyl.dao.CompanyDAO;
import com.oxyl.model.Company;
import com.oxyl.model.Computer;



public class ComputerDTO {
	private String computerId;
	private String computerName;
	private String introductionDate;
	private String discontinuedDate;
	private String manufacturerName;
	private String manufacturerId;
	private static final Logger LOGGER = LoggerFactory.getLogger(ComputerDTO.class);

	
	public ComputerDTO() {
		this.computerId = "";
		this.computerName = "";
		this.introductionDate = "";
		this.discontinuedDate = "";
		this.manufacturerName = "";
		this.manufacturerId = "";
	}
	
	public ComputerDTO(Computer computer) {
		LOGGER.info(computer.toString());
		this.manufacturerId = "";
		this.computerName = computer.getComputerName();
		this.computerId = Integer.toString(computer.getId()).toString();
		LocalDate introductionDate = computer.getIntroductionDate();
		LocalDate discontinuedDate = computer.getDiscontinuedDate();
		if (introductionDate != null) {
			this.introductionDate = introductionDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
		}
		if (discontinuedDate != null) {
			this.discontinuedDate = discontinuedDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
		}
		Company manufacturer = computer.getManufacturer();
		if (manufacturer != null) {
			this.manufacturerName = manufacturer.getName();
			if(!"".equals(manufacturer.getName())) {
				this.manufacturerId = Integer.toString(manufacturer.getId());
			}
		}
	}
	
	public ComputerDTO(String computerId, String computerName, String introductionDate, String discontinuedDate, String manufacturerName, String manufacturerId) {
		this.computerId = computerId;
		this.computerName = computerName;
		this.introductionDate = introductionDate;
		this.discontinuedDate = discontinuedDate;
		this.manufacturerName = manufacturerName;
		this.manufacturerId = manufacturerId;
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
	
	public String getCompanyId() {
		return manufacturerId;
	}
}
