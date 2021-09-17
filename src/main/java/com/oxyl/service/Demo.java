package com.oxyl.service;

import java.sql.Date;
import java.sql.SQLException;
import java.util.Optional;

import com.oxyl.dao.CompanyDAO;
import com.oxyl.dao.ComputerDAO;
import com.oxyl.model.Company;
import com.oxyl.model.Computer;
import com.oxyl.persistence.DatabaseConnection;

public class Demo {
    public static void main( String[] args ) {
		DatabaseConnection db = DatabaseConnection.getInstance();
        try {
        	CompanyDAO companies = CompanyDAO.getInstance();
        	for(Company company : companies.companyList) {
        		System.out.println(company);
        	}
        	ComputerDAO computers = new ComputerDAO();
        	for(Computer computer : computers.getAllComputers()) {
        		System.out.println(computer);
        	}
        	System.out.println(computers.getComputer(12));
        	computers.deleteComputer(12);
        	System.out.println(computers.getComputer(12));
        	Optional<Company> company = companies.getCompany("Sony");
        	computers.insertComputer(new Computer.ComputerBuilder("Test").manufacturer(company).build());
        	computers.updateComputer(new Computer.ComputerBuilder("MacBook Pro 15.4 inch")
        			                             .id(1)
        			                             .introductionDate(new Date(System.currentTimeMillis()))
        			                             .manufacturer(company)
        			                             .build());
        	for(Computer computer : computers.getAllComputers()) {
        		System.out.println(computer);
        	}
			db.connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
        
    }
}
