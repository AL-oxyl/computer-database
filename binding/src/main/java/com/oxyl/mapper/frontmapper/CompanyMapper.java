package com.oxyl.mapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.oxyl.dto.CompanyDTO;
import com.oxyl.model.Company;

@Component
public class CompanyMapper {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(CompanyMapper.class);

	private CompanyMapper() {
		LOGGER.info("Company mapper instantiate");
	}
	
	public static List<CompanyDTO> companyListToDTOList(List<Optional<Company>> companyList) {
		List<CompanyDTO> dtoList = new ArrayList<CompanyDTO>();
		for (Optional<Company> company : companyList) {
			dtoList.add(optionalCompanyModelToCompanyDTO(company));
		}
		return dtoList;
	}
	
	public static List<Company> companyListToModelList(List<Optional<Company>> companyList) {
		List<Company> modelList = new ArrayList<Company>();
		for (Optional<Company> company : companyList) {
			if(company.isPresent()) {
				modelList.add(company.get());
			}
		}
		return modelList;
	}
	
	private static CompanyDTO optionalCompanyModelToCompanyDTO(Optional<Company> company) {
		CompanyDTO dto = new CompanyDTO();
		if (company.isPresent()) {
			return companyModelToCompanyDTO(company.get());
		}
		return dto;
	}
	
	public static CompanyDTO companyModelToCompanyDTO(Company model) {
		return new CompanyDTO(model);
	}
}
