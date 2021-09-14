package com.oxyl.cdb;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.oxyl.persistence.DatabaseConnection;

public class Companies {
	private static Companies instance;
	public ArrayList<Company> companyList;
	static final String QUERY = "select * from company";
	
	private Companies(DatabaseConnection db) {
		try {
			Statement test = db.connection.createStatement();
			ResultSet rs = test.executeQuery(QUERY);
			ArrayList<Company> companyList = new ArrayList<Company>(); 
			while(rs.next()) {
				companyList.add(new Company(rs.getInt(1),rs.getString(2)));
			}
			this.companyList = companyList;
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	
	public static Companies getInstance(DatabaseConnection db) {
		if (instance == null) {
			instance = new Companies(db);
		}
		return instance;
	}
}