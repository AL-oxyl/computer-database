package com.oxyl.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import com.oxyl.model.Company;
import com.oxyl.persistence.DatabaseConnection;

public class Companies {
	/**
	 * This is the class that contains all companies. It follows Singleton pattern.
	 */
	private static Companies instance;
	public ArrayList<Company> companyList;
	private DatabaseConnection db;
	public static final short NUMBER_RESULT_BY_PAGE = 10;
	static final String QUERY_ALL = "select * from company";
	static final String QUERY_GET_BY_ID = "select * from company where id=";
	static final String QUERY_GET_BY_NAME = "select * from company where name=?";
	private static final String QUERY_GET_RANGE = "select * from company limit ?,"+Short.toString(NUMBER_RESULT_BY_PAGE);
	private static final String QUERY_COUNT = "select count(id) from company";
	
	
	private Companies() {
		/**
		 * @param DatabaseConnection
		 */
		try {
			this.db = DatabaseConnection.getInstance();
			Statement statement = db.connection.createStatement();
			ResultSet rs = statement.executeQuery(QUERY_ALL);
			ArrayList<Company> companyList = new ArrayList<Company>(); 
			while(rs.next()) {
				companyList.add(new Company(rs.getInt(1),rs.getString(2)));
			}
			this.companyList = companyList;
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	
	public static Companies getInstance() {
		/**
		 * @param DatabaseConnection
		 * @return Companies
		 */
		if (instance == null) {
			instance = new Companies();
		}
		return instance;
	}
	
	public Company getCompany(int id) {
		try {
	        Statement statement = db.connection.createStatement();
	        ResultSet rs = statement.executeQuery(QUERY_GET_BY_ID + id);
	        if(rs.next()) {
	            return extractCompany(rs);
	        }

	    } catch (SQLException e) {
	        e.printStackTrace();
	    }

	    return null; 
	}
	
	public Company getCompany(String name) {
		try {
	        PreparedStatement ps = db.connection.prepareStatement(QUERY_GET_BY_NAME);
	        ps.setString(1,name);
	        ResultSet rs = ps.executeQuery();
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
	
	public ArrayList<Company> getCompanyRange(int pageNumber) {
		try {
			ArrayList<Company> companyRange = new ArrayList<Company>();
	        PreparedStatement ps = db.connection.prepareStatement(QUERY_GET_RANGE);
	        ps.setInt(1,pageNumber*NUMBER_RESULT_BY_PAGE);
	        ResultSet rs = ps.executeQuery();
	        while(rs.next()) {
	        	companyRange.add(extractCompany(rs));
	        }
	        return companyRange;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public int getCompanyCount() {
		try {
			PreparedStatement ps = db.connection.prepareStatement(QUERY_COUNT);
			ResultSet rs = ps.executeQuery();
	        if(rs.next()) {
	        	return rs.getInt(1);
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return 0; 
	}
}