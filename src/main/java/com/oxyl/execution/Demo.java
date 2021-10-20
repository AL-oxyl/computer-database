package com.oxyl.execution;

import java.util.Optional;

import com.oxyl.dao.CompanyDAO;
import com.oxyl.dao.ComputerDAO;
import com.oxyl.model.Company;
import com.oxyl.model.Computer;

public class Demo {
    public static void main( String[] args ) {
        	CompanyDAO companies = CompanyDAO.getInstance();

       /** 	ComputerDAO computers = ComputerDAO.getInstance();

        	computers.deleteComputer(12);
        	Optional<Company> company = companies.getCompany("Sony");
        	computers.insertComputer(new Computer.ComputerBuilder("Test").manufacturer(company).build());
        	computers.updateComputer(new Computer.ComputerBuilder("MacBook Pro 15.4 inch")
        			                             .id(1)
        			                             .manufacturer(company)
        			                             .build());**/

        
    }
}
