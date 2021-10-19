package com.oxyl.mapper;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.oxyl.dto.ComputerDTO;
import com.oxyl.model.Company;
import com.oxyl.model.Computer;

@Component
public class ComputerMapper {
	private static ComputerMapper instance;
	private static final Logger LOGGER = LoggerFactory.getLogger(ComputerMapper.class);
	private static final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MM-yyyy");

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
		for (Computer computer : computerList) {
			dtoList.add(computerModelToComputerDTO(computer));
		}
		return dtoList;
	}

	public static Computer computerDTOToComputerModel(ComputerDTO dtoComputer, List<Company> companies)  {
		
		String name = "";
		String companyId = "";
		Optional<LocalDate> localIntro = Optional.empty();
		Optional<LocalDate> localDis = Optional.empty();
		Optional <Integer> compId = Optional.empty();
		name = dtoComputer.getComputerName();
		if(!dtoComputer.getIntroductionDate().isEmpty()) {
			localIntro = Optional.of(LocalDate.parse(dtoComputer.getIntroductionDate(),dtf));
		}
		if(!dtoComputer.getDiscontinuedDate().isEmpty()) {
			localDis = Optional.of(LocalDate.parse(dtoComputer.getDiscontinuedDate(),dtf));
		}
		if(dtoComputer.getCompanyId().isPresent()) {
			companyId = dtoComputer.getCompanyId().get();
		}
		if(!"-1".equals(dtoComputer.getComputerId())) {
			compId = Optional.of(Integer.parseInt(dtoComputer.getComputerId()));
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
		for (Company company : companies) {
			if (Integer.parseInt(companyId) == company.getId()) {
				return new Company(company.getId(), company.getName());
			}
		}
		return new Company(0, "");
	}
}
