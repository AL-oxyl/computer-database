package com.oxyl.model;
import java.sql.Date;

public class Computer {
	private final Integer id;
	private final String computerName;
	private final Date introductionDate;
	private final Date discontinuedDate;
	private final Company manufacturer;
	
	public Computer(ComputerBuilder builder) {
		this.computerName = builder.computerName;
		this.id = builder.id;
		this.introductionDate = builder.introductionDate;
		this.discontinuedDate  = builder.discontinuedDate;
		this.manufacturer = builder.manufacturer;
	}
	
	//Setter
	public String getComputerName() {
		return computerName;
	}
	
	public Date getIntroductionDate() {
		return introductionDate;
	}

	public Date getDiscontinuedDate() {
		return discontinuedDate;
	}

	public Company getManufacturer() {
		return manufacturer;
	}

	public int getId() {
		return id;
	}

	@Override
	public String toString() {
		
		return "id : "+this.id+ "\tname : "+this.computerName+ "\tintroduction date : "+this.introductionDate+ "\tdiscontinuedDate : "+this.discontinuedDate
				      + "\tcompany : "+this.manufacturer;
	}
	
	public static class ComputerBuilder {
		private final String computerName;
		private Integer id;
		private Date introductionDate;
		private Date discontinuedDate;
		private Company manufacturer;
		
		public ComputerBuilder(String name) {
			this.computerName = name;
		}
		
		public ComputerBuilder id(int id) {
			this.id = id;
			return this;
		}
		
		public ComputerBuilder introductionDate(Date date) {
			this.introductionDate = date;
			return this;
		}
		
		public ComputerBuilder discontinuedDate(Date date) {
			this.discontinuedDate = date;
			return this;
		}
		
		public ComputerBuilder manufacturer(Company company) {
			this.manufacturer = company;
			return this;
		}
		
		public Computer build() {
			return new Computer(this);
		}
		
	}
}
