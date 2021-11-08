package com.oxyl.model;
import java.time.LocalDate;


public class Computer {

	private final Integer id;
	private final String computerName;
	private final LocalDate introductionDate;
	private final LocalDate discontinuedDate;
	private final Company company;
	
	public Computer(ComputerBuilder builder) {
		this.computerName = builder.computerName;
		this.id = builder.id;
		this.introductionDate = builder.introductionDate;
		this.discontinuedDate  = builder.discontinuedDate;
		this.company = builder.company;
	}
	
	//Getter
	public String getComputerName() {
		return computerName;
	}
	
	public LocalDate getIntroductionDate() {
		return introductionDate;
	}

	public LocalDate getDiscontinuedDate() {
		return discontinuedDate;
	}

	public Company getManufacturer() {
		return company;
	}

	public int getId() {
		return id;
	}

	@Override
	public String toString() {
		
		return "id : "+this.id+ "\tname : "+this.computerName+ "\tintroduction date : "+this.introductionDate+ "\tdiscontinuedDate : "+this.discontinuedDate
				      + "\tcompany : "+this.company;
	}
	
	public static class ComputerBuilder {
		private final String computerName;
		private Integer id;
		private LocalDate introductionDate;
		private LocalDate discontinuedDate;
		private Company company;
		
		public ComputerBuilder(String name) {
			this.computerName = name;
		}
		
		public ComputerBuilder id(int id) {
			this.id = id;
			return this;
		}
		
		public ComputerBuilder introductionDate(LocalDate date) {
			this.introductionDate = date;
			return this;
		}
		
		public ComputerBuilder discontinuedDate(LocalDate date) {
			this.discontinuedDate = date;
			return this;
		}
		
		public ComputerBuilder manufacturer(Company company) {
			this.company = company;
			return this;
		}
		
		public Computer build() {
			return new Computer(this);
		}
		
	}
}
