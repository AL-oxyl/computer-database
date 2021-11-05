package com.oxyl.dto;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.springframework.stereotype.Component;

@Entity
@Table(name = "computer")
public class ComputerPersistDto {

		@Id
		@GeneratedValue(strategy = GenerationType.IDENTITY)
		private Integer id;
		private String name;
		private LocalDate introduced;
		private LocalDate discontinued;
		
		@ManyToOne
		@JoinColumn(name = "company_id", referencedColumnName = "id")
		private CompanyPersistDto company;
		
		public ComputerPersistDto() {
			
		}
		
		public ComputerPersistDto(ComputerPersistDtoBuilder builder) {
			this.name = builder.name;
			this.id = builder.id;
			this.introduced = builder.introduced;
			this.discontinued  = builder.discontinued;
			this.company = builder.company;
		}
		
		//Getter
		public String getComputerName() {
			return name;
		}
		
		public LocalDate getIntroductionDate() {
			return introduced;
		}

		public LocalDate getDiscontinuedDate() {
			return discontinued;
		}

		public CompanyPersistDto getCompany() {
			return company;
		}

		public int getId() {
			return id;
		}

		@Override
		public String toString() {
			
			return "id : "+this.id+ "\tname : "+this.name+ "\tintroduction date : "+this.introduced+ "\tdiscontinuedDate : "+this.discontinued
					      + "\tcompany : "+this.company;
		}
		
		public static class ComputerPersistDtoBuilder {
			private final String name;
			private Integer id;
			private LocalDate introduced;
			private LocalDate discontinued;
			private CompanyPersistDto company;
			
			public ComputerPersistDtoBuilder(String name) {
				this.name = name;
			}
			
			public ComputerPersistDtoBuilder id(int id) {
				this.id = id;
				return this;
			}
			
			public ComputerPersistDtoBuilder introductionDate(LocalDate date) {
				this.introduced = date;
				return this;
			}
			
			public ComputerPersistDtoBuilder discontinuedDate(LocalDate date) {
				this.discontinued = date;
				return this;
			}
			
			public ComputerPersistDtoBuilder manufacturer(CompanyPersistDto company) {
				this.company = company;
				return this;
			}
			
			public ComputerPersistDto build() {
				return new ComputerPersistDto(this);
			}
			
		}
}
