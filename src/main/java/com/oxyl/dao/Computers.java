package com.oxyl.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.oxyl.model.Company;
import com.oxyl.model.Computer;
import com.oxyl.persistence.DatabaseConnection;

public class Computers implements ComputerDao {
	static final String QUERY_SELECT = "select * from computer";
	static final String QUERY_GET = "select * from computer where id=";
	static final String QUERY_INSERT = "insert INTO computer VALUES (NULL, ?, ?, ?, ?)";
	static final String QUERY_UPDATE = "update computer SET name=?, introduced=?, discontinued=?, company_id=? where id=?";
	static final String QUERY_DELETE = "delete from computer where id=";
	private DatabaseConnection db;
	private Companies instance;
	
	
	public Computers(DatabaseConnection db) {
		this.db = db;
		this.instance = Companies.getInstance(db);
	}
	
	public Computer getComputer(int id) {
		try {
	        Statement statement = db.connection.createStatement();
	        ResultSet rs = statement.executeQuery(QUERY_GET + id);
	        if(rs.next()) {
	            return extractComputer(rs);
	        }

	    } catch (SQLException e) {
	        e.printStackTrace();
	    }

	    return null; 
	}
	
	private Computer extractComputer(ResultSet rs) throws SQLException {
		Company company = instance.getCompany(rs.getInt(5));
		return new Computer.ComputerBuilder(rs.getString(2)).id(rs.getInt(1)).introductionDate(rs.getDate(3)).discontinuedDate(rs.getDate(4)).manufacturer(company).build();
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
	
	public boolean insertComputer(Computer computer) {
		try {
			PreparedStatement ps = db.connection.prepareStatement(QUERY_INSERT);
	        ps.setString(1, computer.getComputerName());
	        ps.setDate(2, computer.getIntroductionDate());
	        ps.setDate(3, computer.getDiscontinuedDate());
	        ps.setInt(4, computer.getManufacturer().getId());
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
	        if(computer.getManufacturer()!= null) {
	        	ps.setInt(4, computer.getManufacturer().getId());
	        }
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
}
