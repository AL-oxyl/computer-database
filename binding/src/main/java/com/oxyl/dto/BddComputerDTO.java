package com.oxyl.dto;

import java.time.LocalDate;

public class BddComputerDTO {
	private final Integer computerId;
	private final String computerName;
	private final LocalDate introductionDate;
	private final LocalDate discontinuedDate;
	private final Integer manufacturerId;
	
	public BddComputerDTO(Integer computerId, String computerName, LocalDate introductionDate,
			              LocalDate discontinuedDate, Integer manufacturerId) {
		this.computerId = computerId;
		this.computerName = computerName;
		this.introductionDate = introductionDate;
		this.discontinuedDate = discontinuedDate;
		this.manufacturerId = manufacturerId;
	}

	public Integer getComputerId() {
		return computerId;
	}

	public String getComputerName() {
		return computerName;
	}

	public LocalDate getIntroductionDate() {
		return introductionDate;
	}

	public LocalDate getDiscontinuedDate() {
		return discontinuedDate;
	}

	public Integer getManufacturerId() {
		return manufacturerId;
	}
	
	
}
