package com.oxyl.dto;

import java.sql.Timestamp;

import java.util.Optional;

public class BddComputerDTO {
	private final int computerId;
	private final Optional<String> computerName;
	private final Optional<Timestamp> introductionDate;
	private final Optional<Timestamp> discontinuedDate;
	private final Optional<Integer> manufacturerId;
	
	public BddComputerDTO(int computerId, Optional<String> computerName, Optional<Timestamp> introductionDate,
			              Optional<Timestamp> discontinuedDate, Optional<Integer> manufacturerId) {
		this.computerId = computerId;
		this.computerName = computerName;
		this.introductionDate = introductionDate;
		this.discontinuedDate = discontinuedDate;
		this.manufacturerId = manufacturerId;
	}

	public int getComputerId() {
		return computerId;
	}

	public Optional<String> getComputerName() {
		return computerName;
	}

	public Optional<Timestamp> getIntroductionDate() {
		return introductionDate;
	}

	public Optional<Timestamp> getDiscontinuedDate() {
		return discontinuedDate;
	}

	public Optional<Integer> getManufacturerId() {
		return manufacturerId;
	}
	
	
}
