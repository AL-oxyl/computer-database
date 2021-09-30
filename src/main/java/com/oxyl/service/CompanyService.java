package com.oxyl.service;

import java.util.List;

import com.oxyl.dao.CompanyDAO;
import com.oxyl.model.Company;

public class CompanyService {
	public static List<Company> getCompanies() {
		CompanyDAO companyDao = CompanyDAO.getInstance();
		List<Company> companies = companyDao.getAllCompanies();
		return companies;
	}
}
