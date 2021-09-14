package com.oxyl.cdb;
import java.util.Date;

public class Computer {
	private String computerName;
	private Date introductionDate;
	private Date discontinuedDate;
	private String manufacturer;
	
	public Computer(String computerName, Date introductionDate, Date discontinuedDate, String manufacturer) {
		this.computerName = computerName;
		this.introductionDate = introductionDate;
		this.discontinuedDate  = discontinuedDate;
		this.manufacturer = manufacturer;
	}
	
	//Getter Setter
	public String getComputerName() {
		return computerName;
	}
	public void setComputerName(String computerName) {
		this.computerName = computerName;
	}
	public Date getIntroductionDate() {
		return introductionDate;
	}
	public void setIntroductionDate(Date introductionDate) {
		this.introductionDate = introductionDate;
	}
	public Date getDiscontinuedDate() {
		return discontinuedDate;
	}
	public void setDiscontinuedDate(Date discontinuedDate) {
		this.discontinuedDate = discontinuedDate;
	}
	public String getManufacturer() {
		return manufacturer;
	}
	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}
}
