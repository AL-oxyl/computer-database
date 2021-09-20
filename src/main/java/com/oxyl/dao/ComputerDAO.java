package com.oxyl.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.oxyl.model.Company;
import com.oxyl.model.Computer;
import com.oxyl.persistence.DatabaseConnection;

public class ComputerDAO implements ComputerDao {
	public static final short NUMBER_RESULT_BY_PAGE = 10;
	private static final String QUERY_SELECT = "select id,name,introduced,discontinued,company_id from computer";
	private static final String QUERY_GET = "select id,name,introduced,discontinued,company_id from computer where id=";
	private static final String QUERY_INSERT = "insert INTO computer VALUES (NULL, ?, ?, ?, ?)";
	private static final String QUERY_UPDATE = "update computer SET name=?, introduced=?, discontinued=?, company_id=? where id=?";
	private static final String QUERY_DELETE = "delete from computer where id=";
	private static final String QUERY_GET_RANGE = "select id,name,introduced,discontinued,company_id from computer limit ?,"+Short.toString(NUMBER_RESULT_BY_PAGE);
	private static final String QUERY_COUNT = "select count(id) from computer";
	
	private DatabaseConnection db;
	private CompanyDAO instance;
	
	
	public ComputerDAO() {
		this.db = DatabaseConnection.getInstance();
		this.instance = CompanyDAO.getInstance();
	}
	
	public Computer getComputer(int id) throws SQLException {
	    Statement statement = db.connection.createStatement();
	    ResultSet rs = statement.executeQuery(QUERY_GET + id);
	    if(rs.next()) {
	         return extractComputer(rs);
	    }
	    return null; 
	}
	
	private Computer extractComputer(ResultSet rs) throws SQLException {
		Optional<Company> company = instance.getCompany(rs.getInt(5));
		return new Computer.ComputerBuilder(rs.getString(2))
				           .id(rs.getInt(1))
				           .introductionDate(rs.getDate(3))
				           .discontinuedDate(rs.getDate(4))
				           .manufacturer(company).build();
	}
	
	public List<Computer> getAllComputers() {
		List<Computer> companyList = new ArrayList<Computer>(); 
		try {
			Statement test = db.connection.createStatement();
			ResultSet rs = test.executeQuery(QUERY_SELECT);
			while(rs.next()) {
				companyList.add(extractComputer(rs));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return companyList;
	}
	
	public ArrayList<Computer> getComputerRange(int pageNumber) {
		try {
			ArrayList<Computer> computerRange = new ArrayList<Computer>();
	        PreparedStatement ps = db.connection.prepareStatement(QUERY_GET_RANGE);
	        ps.setInt(1,pageNumber*NUMBER_RESULT_BY_PAGE);
	        ResultSet rs = ps.executeQuery();
	        while(rs.next()) {
	        	computerRange.add(extractComputer(rs));
	        }
	        return computerRange;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public boolean insertComputer(Computer computer) {
		try {
			PreparedStatement ps = db.connection.prepareStatement(QUERY_INSERT);
	        ps.setString(1, computer.getComputerName());
	        ps.setDate(2, computer.getIntroductionDate());
	        ps.setDate(3, computer.getDiscontinuedDate());
	        ps.setInt(4, computer.getManufacturer().map(Company::getId).orElse(null));
	        int i = ps.executeUpdate();

	      if(i == 1) {
	        return true;
	      }

	    } catch (SQLException e) {
	        e.printStackTrace();
	    } catch (NullPointerException e) {
	    	System.out.println("The company hasn't properly been initialised");
	    	e.printStackTrace();
	    }

	    return false;
	}
	
	public boolean updateComputer(Computer computer) {
		try {
			PreparedStatement ps = db.connection.prepareStatement(QUERY_UPDATE);
	        ps.setString(1, computer.getComputerName());
	        ps.setDate(2, computer.getIntroductionDate());
	        ps.setDate(3, computer.getDiscontinuedDate());
	        ps.setInt(4, computer.getManufacturer().map(Company::getId).orElse(null));
	        ps.setInt(5, computer.getId());
	        int i = ps.executeUpdate();

	      if(i == 1) {
	        return true;
	      }

	    } catch (SQLException e) {
	        e.printStackTrace();
	    }

	    return false;
	}
	
	public boolean deleteComputer(int id) {
		try {
	        Statement statement = db.connection.createStatement();
	        int result = statement.executeUpdate(QUERY_DELETE + id);
	        if(result == 1) {
	            return true;
	        }

	    } catch (SQLException e) {
	        e.printStackTrace();
	    }

	    return false; 
	}
	
	public int getComputerCount() {
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

