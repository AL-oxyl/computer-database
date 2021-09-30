package com.oxyl.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.oxyl.mapper.BddMapper;
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
	private static final Logger LOGGER = LoggerFactory.getLogger(ComputerDAO.class);

	private DatabaseConnection db;
	private CompanyDAO instance;
	
	
	public ComputerDAO() {
		this.db = DatabaseConnection.getInstance();
		this.instance = CompanyDAO.getInstance();
	}
	
	public Optional<Computer> getComputer(int id) throws SQLException {
	    Statement statement = db.connection.createStatement();
	    ResultSet rs = statement.executeQuery(QUERY_GET + id);
	    if(rs.next()) {
	         return Optional.of(extractComputer(rs));
	    }
	    return Optional.empty(); 
	}
	
	private Computer extractComputer(ResultSet rs) throws SQLException {
		Optional<Company> company = instance.getCompany(rs.getInt(5));
		return new Computer.ComputerBuilder(rs.getString(2))
				           .id(rs.getInt(1))
				           .introductionDate(BddMapper.optTimestampToOptLocalDate(Optional.ofNullable(rs.getTimestamp(3))))
				           .discontinuedDate(BddMapper.optTimestampToOptLocalDate(Optional.ofNullable(rs.getTimestamp(4))))
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
			LOGGER.error("Unable to query all in computer table",e);
		}
		return companyList;
	}
	
	public ArrayList<Computer> getComputerRange(int pageNumber) {
		ArrayList<Computer> computerRange = new ArrayList<Computer>();
		try {
			
	        PreparedStatement ps = db.connection.prepareStatement(QUERY_GET_RANGE);
	        ps.setInt(1,pageNumber*NUMBER_RESULT_BY_PAGE);
	        ResultSet rs = ps.executeQuery();
	        while(rs.next()) {
	        	computerRange.add(extractComputer(rs));
	        }
	        return computerRange;
		} catch (SQLException e) {
			LOGGER.error("Unable to query a range in computer table",e);
		}
		return computerRange;
	}
	
	public int insertComputer(Computer computer) {
		int result = 0;
		try {
			PreparedStatement ps = db.connection.prepareStatement(QUERY_INSERT);
			Optional<Timestamp> introDate = BddMapper.optLocalDateToOptTimestamp(computer.getIntroductionDate());
			Optional<Timestamp> disDate = BddMapper.optLocalDateToOptTimestamp(computer.getDiscontinuedDate());
	        ps.setString(1, computer.getComputerName());
	        ps.setTimestamp(2, introDate.orElse(null));
	        ps.setTimestamp(3, disDate.orElse(null));
	        ps.setInt(4, computer.getManufacturer().map(Company::getId).orElse(null));
	        result = ps.executeUpdate();
	    } catch (SQLException e) {
	        LOGGER.error("Unable to insert a computer in computer table",e);
	    } catch (NullPointerException e) {
	    	LOGGER.error("The company hasn't properly been initialised",e);
	    }

	    return result;
	}
	
	public boolean updateComputer(Computer computer) {
		try {
			PreparedStatement ps = db.connection.prepareStatement(QUERY_UPDATE);
			Optional<Timestamp> introDate = BddMapper.optLocalDateToOptTimestamp(computer.getIntroductionDate());
			Optional<Timestamp> disDate = BddMapper.optLocalDateToOptTimestamp(computer.getDiscontinuedDate());
	        ps.setString(1, computer.getComputerName());
	        ps.setTimestamp(2, introDate.orElse(null));
	        ps.setTimestamp(3, disDate.orElse(null));
	        ps.setInt(4, computer.getManufacturer().map(Company::getId).orElse(null));
	        ps.setInt(5, computer.getId());
	        int result = ps.executeUpdate();
	        if (result == 1) {
	        	return true;
	        }
	    } catch (SQLException e) {
	        LOGGER.error("Unable to update a computer in computer table",e);
	    }
	    return false;
	}
	
	public int deleteComputer(int id) {
		int result = 0;
		try {
	        Statement statement = db.connection.createStatement();
	        result = statement.executeUpdate(QUERY_DELETE + id);
	    } catch (SQLException e) {
	        LOGGER.error("Unable to delete a computer in computer table",e);
	    }
		return result;
	}
	
	public int getComputerCount() {
		try {
			PreparedStatement ps = db.connection.prepareStatement(QUERY_COUNT);
			ResultSet rs = ps.executeQuery();
	        if(rs.next()) {
	        	return rs.getInt(1);
	        }
	    } catch (SQLException e) {
	        LOGGER.error("Unable to get the computer count in computer table",e);
	    }
	    return 0; 
	}
}

