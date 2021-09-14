package com.oxyl.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;

import com.oxyl.model.Company;
import com.oxyl.model.Computer;
import com.oxyl.persistence.DatabaseConnection;

public class Companies {
	/**
	 * This is the class that contains all companies. It follows Singleton pattern.
	 */
	private static Companies instance;
	public LinkedList<Company> companyList;
	private DatabaseConnection db;
	static final String QUERY_ALL = "select * from company";
	static final String QUERY_GET = "select * from company where id=";
	
	
	private Companies(DatabaseConnection db) {
		/**
		 * @param DatabaseConnection
		 */
		try {
			this.db = db;
			Statement statement = db.connection.createStatement();
			ResultSet rs = statement.executeQuery(QUERY_ALL);
			LinkedList<Company> companyList = new LinkedList<Company>(); 
			while(rs.next()) {
				companyList.add(new Company(rs.getInt(1),rs.getString(2)));
			}
			this.companyList = companyList;
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	
	public static Companies getInstance(DatabaseConnection db) {
		/**
		 * @param DatabaseConnection
		 * @return Companies
		 */
		if (instance == null) {
			instance = new Companies(db);
		}
		return instance;
	}
	
	public Company getCompany(int id) {
		try {
	        Statement statement = db.connection.createStatement();
	        ResultSet rs = statement.executeQuery(QUERY_GET + id);
	        if(rs.next()) {
	            return extractCompany(rs);
	        }

	    } catch (SQLException e) {
	        e.printStackTrace();
	    }

	    return null; 
	}
	
	private Company extractCompany(ResultSet rs) throws SQLException {
		return new Company(rs.getInt(1),rs.getString(2));
	}
	
}