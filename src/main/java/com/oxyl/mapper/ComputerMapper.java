package com.oxyl.mapper;


import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.oxyl.dto.ComputerDTO;
import com.oxyl.model.Company;
import com.oxyl.model.Computer;
import com.oxyl.service.CompanyService;
import com.oxyl.validator.ComputerValidator;

public class ComputerMapper {
	private static ComputerMapper instance;
	private static final Logger LOGGER = LoggerFactory.getLogger(ComputerMapper.class);
	private static final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MMM-dd");
	
	private ComputerMapper() {
		LOGGER.info("Computer mapper instantiate");
	}

	public static ComputerMapper getInstance() {
		if (instance == null) {
			instance = new ComputerMapper();
		}
		return instance;
	}
	
	public static ComputerDTO computerModelToComputerDTO(Computer model) {
		return new ComputerDTO(model);
	}
	
	public static ArrayList<ComputerDTO> computerListToDTOList(ArrayList<Computer> computerList) {
		ArrayList<ComputerDTO> dtoList = new ArrayList<ComputerDTO>();
		for(Computer computer : computerList) {
			dtoList.add(computerModelToComputerDTO(computer));
		}
		return dtoList;
	}
	
	public static Computer computerDTOToComputerModel(ComputerDTO dtoComputer, List<Company> companies){
		String name = "";
		String companyId = "";
		Optional<LocalDateTime> localIntro = Optional.empty();
		Optional<LocalDateTime> localDis = Optional.empty();
		Optional <Integer> compId = Optional.empty();
		if(!ComputerValidator.checkComputer(dtoComputer,companies)) {
			return new Computer.ComputerBuilder("").build();
		}
		name = dtoComputer.getComputerName();
		if(!"-1".equals(dtoComputer.getComputerId()) ) {
			compId = Optional.of(Integer.parseInt(dtoComputer.getComputerId()));
		}
		if(!"".equals(dtoComputer.getIntroductionDate())) {
			localIntro = Optional.of(LocalDateTime.parse(dtoComputer.getIntroductionDate(),dtf));
		}
		if(!"".equals(dtoComputer.getDiscontinuedDate())) {
			localDis = Optional.of(LocalDateTime.parse(dtoComputer.getDiscontinuedDate(),dtf));
		}
		if(dtoComputer.getId().isPresent()) {
			companyId = dtoComputer.getId().get();
		}
		if(!"-1".equals(dtoComputer.getComputerId())) {
			return new Computer.ComputerBuilder(name)
			           .id(compId.get())
			           .manufacturer(Optional.ofNullable(companyIdToCompanyModel(companyId, companies)))
			           .introductionDate(localIntro)
			           .discontinuedDate(localDis)
			           .build(); 
		}
		return new Computer.ComputerBuilder(name)
				           .manufacturer(Optional.ofNullable(companyIdToCompanyModel(companyId, companies)))
				           .introductionDate(localIntro)
				           .discontinuedDate(localDis)
				           .build();     
	}
	
	public static Company companyIdToCompanyModel(String companyId, List<Company> companies) {
		for(Company company : companies) {
			if ( Integer.parseInt(companyId) == company.getId()) {
				return new Company(company.getId(),company.getName());
			}
		}
		return new Company(0,"");
	}	
}
 