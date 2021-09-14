package com.oxyl.cdb;

import com.oxyl.persistence.DatabaseConnection;

import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;
/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) {
		DatabaseConnection db = DatabaseConnection.getInstance();
        try {
        	Companies companies = Companies.getInstance(db);
        	for(Company company : companies.companyList) {
        		System.out.println(company.getName());
        	}
        	
		/*	rs = test.executeQuery("select * from computer");
			while(rs.next()) {
				System.out.println(rs.getString(2) + " " + rs.getDate(3) + " " + rs.getDate(4) + " " + rs.getString(5));
			}*/
			db.connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
        
    }
}
