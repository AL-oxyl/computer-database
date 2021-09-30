package com.oxyl.execution;

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

        	ComputerDAO computers = new ComputerDAO();

        	computers.deleteComputer(12);
        	Optional<Company> company = companies.getCompany("Sony");
        	computers.insertComputer(new Computer.ComputerBuilder("Test").manufacturer(company).build());
        	computers.updateComputer(new Computer.ComputerBuilder("MacBook Pro 15.4 inch")
        			                             .id(1)
        			                             .manufacturer(company)
        			                             .build());
			db.connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
        
    }
}
