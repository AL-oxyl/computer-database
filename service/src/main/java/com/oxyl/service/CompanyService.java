package com.oxyl.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.oxyl.dao.CompanyDAO;
import com.oxyl.mapper.frontmapper.BddMapper;
import com.oxyl.model.Company;

@Service
public class CompanyService {
	
	CompanyDAO companyDao;
	
	@Autowired
	public CompanyService(CompanyDAO companyDao) {
		this.companyDao = companyDao;
	}
	public List<Company> getCompanies() {
		List<Company> companies = BddMapper.companyPersistDtoListToCompanyModelList(companyDao.getAllCompanies());
		return companies;
	}
}
