package com.oxyl.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.oxyl.model.Company;
import com.oxyl.model.Computer;
import com.oxyl.persistence.DatabaseConnection;

public class CompanyDAO {
	/**
	 * This is the class that contains all companies. It follows Singleton pattern.
	 */
	private static CompanyDAO instance;
	public ArrayList<Company> companyList;
	private DatabaseConnection db;
	public static final short NUMBER_RESULT_BY_PAGE = 10;
	private static final String QUERY_ALL = "select id,name from company";
	private static final String QUERY_GET_BY_ID = "select id,name from company where id=";
	private static final String QUERY_GET_BY_NAME = "select id,name from company where name=?";
	private static final String QUERY_GET_RANGE = "select id,name from company limit ?,"+Short.toString(NUMBER_RESULT_BY_PAGE);
	private static final String QUERY_COUNT = "select count(id) from company";
	private static final Logger LOGGER = LoggerFactory.getLogger(CompanyDAO.class);
	
	
	private CompanyDAO() {
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
			LOGGER.error("Unable to query all in company table",e);
		}
	}
	
	
	public static CompanyDAO getInstance() {
		/**
		 * @param DatabaseConnection
		 * @return Companies
		 */
		if (instance == null) {
			instance = new CompanyDAO();
		}
		return instance;
	}
	
	public Optional<Company> getCompany(int id) {
		try {
	        Statement statement = db.connection.createStatement();
	        ResultSet rs = statement.executeQuery(QUERY_GET_BY_ID + id);
	        if(rs.next()) {
	            return Optional.of(extractCompany(rs));
	        }

	    } catch (SQLException e) {
			LOGGER.error("Unable to query a company at the specified id in company table",e);
	    }
		return Optional.empty();
	}
	
	public Optional<Company> getCompany(String name) {
		try {
	        PreparedStatement ps = db.connection.prepareStatement(QUERY_GET_BY_NAME);
	        ps.setString(1,name);
	        ResultSet rs = ps.executeQuery();
	        if(rs.next()) {
	            return Optional.of(extractCompany(rs));
	        }

	    } catch (SQLException e) {
			LOGGER.error("Unable to query a company at the specified name in company table",e);
	    }
	    return Optional.empty();
	}
	
	private Company extractCompany(ResultSet rs) throws SQLException {
		return new Company(rs.getInt(1),rs.getString(2));
	}
	
	public ArrayList<Company> getCompanyRange(int pageNumber) {
		ArrayList<Company> companyRange = new ArrayList<Company>();
		try {
	        PreparedStatement ps = db.connection.prepareStatement(QUERY_GET_RANGE);
	        ps.setInt(1,pageNumber*NUMBER_RESULT_BY_PAGE);
	        ResultSet rs = ps.executeQuery();
	        while(rs.next()) {
	        	companyRange.add(extractCompany(rs));
	        }
		} catch (SQLException e) {
			LOGGER.error("Unable to query a company range in company table",e);
		}
		return companyRange;
	}
	
	public int getCompanyCount() {
		try {
			PreparedStatement ps = db.connection.prepareStatement(QUERY_COUNT);
			ResultSet rs = ps.executeQuery();
	        if(rs.next()) {
	        	return rs.getInt(1);
	        }
	    } catch (SQLException e) {
			LOGGER.error("Unable to query the company count in company table",e);
	    }
	    return 0; 
	}
	
	public List<Company> getAllCompanies() {
		List<Company> companyList = new ArrayList<Company>(); 
		try {
			Statement test = db.connection.createStatement();
			ResultSet rs = test.executeQuery(QUERY_ALL);
			while(rs.next()) {
				companyList.add(extractCompany(rs));
			}
		} catch (SQLException e) {
			LOGGER.error("Unable to query all in company table",e);
		}
		return companyList;
	}
}