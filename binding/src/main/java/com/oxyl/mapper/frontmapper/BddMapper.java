package com.oxyl.mapper.frontmapper;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.oxyl.dto.ComputerPersistDto;
import com.oxyl.dto.ComputerPersistDto.ComputerPersistDtoBuilder;
import com.oxyl.model.Company;
import com.oxyl.dto.CompanyPersistDto;
import com.oxyl.model.Computer;
import com.oxyl.model.Computer.ComputerBuilder;

@Component
public class BddMapper {
	
	public static ComputerPersistDto computerModelToComputerPersistDto(Computer computer) {
		Company company = computer.getManufacturer();
		ComputerPersistDtoBuilder computerDtoBuilder = new ComputerPersistDto.ComputerPersistDtoBuilder(computer.getComputerName());
		computerDtoBuilder.introductionDate(computer.getIntroductionDate())
		                  .discontinuedDate(computer.getDiscontinuedDate())
		                  .id(computer.getId());
		if(company != null) {
			CompanyPersistDto companyDto = companyModelToCompanyPersistDto(company);
			computerDtoBuilder.manufacturer(companyDto);
		}
		return computerDtoBuilder.build();                  
	}
	
	public static CompanyPersistDto companyModelToCompanyPersistDto(Company company) {
		return new CompanyPersistDto(company.getId(),company.getName());             
	}
	
	public static Company companyPersistDtoToCompanyModel(CompanyPersistDto companyDto) {
		return new Company(companyDto.getId(),companyDto.getName());             
	}
	
	public static List<Computer> computerPersistDtoListToComputerModelList(List<ComputerPersistDto> computerDtoList) {
		return computerDtoList.stream()
				              .map(BddMapper::computerPersistDtoToComputerModel)
				              .collect(Collectors.toList());
	}
	
	public static List<Company> companyPersistDtoListToCompanyModelList(List<CompanyPersistDto> companyDtoList) {
		return companyDtoList.stream()
				              .map(BddMapper::companyPersistDtoToCompanyModel)
				              .collect(Collectors.toList());
	}
	
	
	

	public static Optional<Computer> computerPersistDtoToComputerModel(Optional<ComputerPersistDto> computerDto) {
		if(computerDto.isPresent()) {
			ComputerBuilder computerBuilder = new Computer.ComputerBuilder(computerDto.get().getComputerName());
			computerBuilder.introductionDate(computerDto.get().getIntroductionDate())
			               .discontinuedDate(computerDto.get().getDiscontinuedDate())
			               .id(computerDto.get().getId());
			CompanyPersistDto companyDto = computerDto.get().getCompany();
			if(companyDto != null) {
				Company company = companyPersistDtoToCompanyModel(companyDto);
				computerBuilder.manufacturer(company);
			}
			return Optional.of(computerBuilder.build());
		}
		return Optional.empty();
	}
	
	public static Computer computerPersistDtoToComputerModel(ComputerPersistDto computerDto) {
	
		ComputerBuilder computerBuilder = new Computer.ComputerBuilder(computerDto.getComputerName());
		computerBuilder.introductionDate(computerDto.getIntroductionDate())
		               .discontinuedDate(computerDto.getDiscontinuedDate())
		               .id(computerDto.getId());
		CompanyPersistDto companyDto = computerDto.getCompany();
		if(companyDto != null) {
			Company company = companyPersistDtoToCompanyModel(companyDto);
			computerBuilder.manufacturer(company);
		}
		return computerBuilder.build();
	
	
	}
}