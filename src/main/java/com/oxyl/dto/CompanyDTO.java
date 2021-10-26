package com.oxyl.dto;


import com.oxyl.model.Company;

public class CompanyDTO {
	private final String id;
	private final String name;
	
	public CompanyDTO() {
		this.id = "";
		this.name = "";
	}
	
	public CompanyDTO(Company company) {
		this.id = Integer.toString(company.getId());
		this.name = company.getName();
	}

	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}
}
