package com.oxyl.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.oxyl.dao.CompanyDAO;
import com.oxyl.model.Company;

@Service
public class CompanyService {
	
	CompanyDAO companyDao;
	
	@Autowired
	public CompanyService(CompanyDAO companyDao) {
		this.companyDao = companyDao;
	}
	public List<Optional<Company>> getCompanies() {
		List<Optional<Company>> companies = companyDao.getAllCompanies();
		return companies;
	}
}
