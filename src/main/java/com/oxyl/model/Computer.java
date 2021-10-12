package com.oxyl.model;
import java.time.LocalDate;
import java.util.Optional;

public class Computer {
	private final Integer id;
	private final String computerName;
	private final Optional<LocalDate> introductionDate;
	private final Optional<LocalDate> discontinuedDate;
	private final Optional<Company> manufacturer;
	
	public Computer(ComputerBuilder builder) {
		this.computerName = builder.computerName;
		this.id = builder.id;
		this.introductionDate = builder.introductionDate;
		this.discontinuedDate  = builder.discontinuedDate;
		this.manufacturer = builder.manufacturer;
	}
	
	//Getter
	public String getComputerName() {
		return computerName;
	}
	
	public Optional<LocalDate> getIntroductionDate() {
		return introductionDate;
	}

	public Optional<LocalDate> getDiscontinuedDate() {
		return discontinuedDate;
	}

	public Optional<Company> getManufacturer() {
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
		private Optional<LocalDate> introductionDate;
		private Optional<LocalDate> discontinuedDate;
		private Optional<Company> manufacturer;
		
		public ComputerBuilder(String name) {
			this.computerName = name;
		}
		
		public ComputerBuilder id(int id) {
			this.id = id;
			return this;
		}
		
		public ComputerBuilder introductionDate(Optional<LocalDate> date) {
			this.introductionDate = date;
			return this;
		}
		
		public ComputerBuilder discontinuedDate(Optional<LocalDate> date) {
			this.discontinuedDate = date;
			return this;
		}
		
		public ComputerBuilder manufacturer(Optional<Company> company) {
			this.manufacturer = company;
			return this;
		}
		
		public Computer build() {
			return new Computer(this);
		}
		
	}
}
