package com.oxyl.mapper;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.oxyl.dto.ComputerDTO;
import com.oxyl.model.Company;
import com.oxyl.model.Computer;

@Component
public class ComputerMapper {
	private static final Logger LOGGER = LoggerFactory.getLogger(ComputerMapper.class);
	private static final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MM-yyyy");

	private ComputerMapper() {
		LOGGER.info("Computer mapper instantiate");
	}

	public static ComputerDTO computerModelToComputerDTO(Computer computer) {
		return new ComputerDTO(computer);
	}

	public static List<ComputerDTO> computerListToDTOList(List<Computer> computerList) {
		return computerList.stream().map(ComputerMapper::computerModelToComputerDTO).collect(Collectors.toList());
	}
	
	private static ComputerDTO ComputerModelToComputerDTO(Optional<Computer> computer) {
		ComputerDTO dto = new ComputerDTO();
		if (computer.isPresent()) {
			return computerModelToComputerDTO(computer.get());
		}
		return dto;
	}

	public static Computer computerDTOToComputerModel(ComputerDTO dtoComputer, List<Company> companies)  {
		
		String name = dtoComputer.getComputerName();
		String companyId = dtoComputer.getCompanyId();
		LocalDate localIntro = null;
		LocalDate localDis = null;

		if(dtoComputer.getIntroductionDate() != null) {
			localIntro = LocalDate.parse(dtoComputer.getIntroductionDate(),dtf);
		}
		if(dtoComputer.getDiscontinuedDate() != null) {
			localDis = LocalDate.parse(dtoComputer.getDiscontinuedDate(),dtf);
		}
		if(!"-1".equals(dtoComputer.getComputerId())) {
			Integer compId = Integer.parseInt(dtoComputer.getComputerId());
			return new Computer.ComputerBuilder(name)
					           .id(compId)
					           .manufacturer(companyIdToCompanyModel(companyId, companies))
					           .introductionDate(localIntro)
					           .discontinuedDate(localDis)
					           .build(); 
		}
		return new Computer.ComputerBuilder(name)
				    .manufacturer(companyIdToCompanyModel(companyId, companies))
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
