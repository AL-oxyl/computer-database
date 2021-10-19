package com.oxyl.dao;

import java.sql.Connection;

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
import com.oxyl.persistence.DataSource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;


@Repository
public class ComputerDAO implements ComputerDao {
	public static final short NUMBER_RESULT_BY_PAGE = 10;
	private static final String QUERY_SELECT = "select computer.id,computer.name,computer.introduced,computer.discontinued,computer.company_id from computer";
	private static final String QUERY_GET = QUERY_SELECT + "where id=";
	private static final String QUERY_INSERT = "insert INTO computer VALUES (NULL, ?, ?, ?, ?)";
	private static final String QUERY_UPDATE = "update computer SET name=?, introduced=?, discontinued=?, company_id=? where id=?";
	private static final String QUERY_DELETE = "delete from computer where id=";
	private static final String RANGE = " limit ?,"+Short.toString(NUMBER_RESULT_BY_PAGE);
	private static final String QUERY_GET_RANGE = QUERY_SELECT + RANGE;
	private static final String QUERY_COUNT = "select count(computer.id) from computer";
	private static final String LEFT_JOIN_COMPUTER_COMPANY = " LEFT JOIN company ON computer.company_id = company.id";
	private static final String WHERE_COMPUTER_NAME = " where computer.name LIKE ?";
	private static final String WHERE_COMPANY_NAME = "company.name LIKE ?";
	private static final String QUERY_SEARCH_GET_RANGE = QUERY_SELECT 
														+ LEFT_JOIN_COMPUTER_COMPANY 
														+ WHERE_COMPUTER_NAME 
														+ " or " + WHERE_COMPANY_NAME 
														+ RANGE;
	private static final String QUERY_COUNT_SEARCH = QUERY_COUNT 
											+ LEFT_JOIN_COMPUTER_COMPANY 
											+ WHERE_COMPUTER_NAME 
											+ " or " + WHERE_COMPANY_NAME;
	private static final Logger LOGGER = LoggerFactory.getLogger(ComputerDAO.class);
	
	private DataSource dsConnection;
	private static ComputerDAO instance;
	
	
	public static ComputerDAO getInstance() {
		/**
		 * @param DatabaseConnection
		 * @return Companies
		 */
		if (instance == null) {
			instance = new ComputerDAO();
		}
		return instance;
	}
	
	
	private ComputerDAO() {
		LOGGER.info("instantiation computer DAO");
		this.dsConnection = DataSource.getInstance();;
	}
	
	public Optional<Computer> getComputer(int id) {
		try(Connection connection = dsConnection.getConnection();
			Statement statement = connection.createStatement()) {
	    
		    ResultSet rs = statement.executeQuery(QUERY_GET + id);
		    if(rs.next()) {
		         return Optional.of(extractComputer(rs));
		    }
		} catch (SQLException exception) {
			LOGGER.error("Unable to retrieve the computer with id : " + id);
		}
	    return Optional.empty(); 
	}
	
	private Computer extractComputer(ResultSet rs) throws SQLException {
		CompanyDAO companyInstance = CompanyDAO.getInstance();
		Optional<Company> company = companyInstance.getCompany(rs.getInt(5));
		return new Computer.ComputerBuilder(rs.getString(2))
				           .id(rs.getInt(1))
				           .introductionDate(BddMapper.optTimestampToOptLocalDate(Optional.ofNullable(rs.getTimestamp(3))))
				           .discontinuedDate(BddMapper.optTimestampToOptLocalDate(Optional.ofNullable(rs.getTimestamp(4))))
				           .manufacturer(company).build();
	}
	
	public List<Computer> getAllComputers() {
		List<Computer> companyList = new ArrayList<Computer>(); 
		try(Connection connection = dsConnection.getConnection();
			Statement statement = connection.createStatement()) {
			ResultSet rs = statement.executeQuery(QUERY_SELECT);
			while(rs.next()) {
				companyList.add(extractComputer(rs));
			}
		} catch (SQLException e) {
			LOGGER.error("Unable to query all in computer table",e);
		}
		return companyList;
	}
	
	public ArrayList<Computer> getSearchedComputerRange(int pageNumber, String searchedName) {
		ArrayList<Computer> computerRange = new ArrayList<Computer>();
		try(Connection connection = dsConnection.getConnection();
		    PreparedStatement ps = connection.prepareStatement(QUERY_SEARCH_GET_RANGE)) {
			
	        ps.setString(1,"%"+searchedName+"%");
	        ps.setString(2,"%"+searchedName+"%");
	        ps.setInt(3,pageNumber*NUMBER_RESULT_BY_PAGE);
	        LOGGER.info(ps.toString());
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
	
	public ArrayList<Computer> getComputerRange(int pageNumber) {
		ArrayList<Computer> computerRange = new ArrayList<Computer>();
		try(Connection connection = dsConnection.getConnection();
		    PreparedStatement ps = connection.prepareStatement(QUERY_GET_RANGE)) {
			
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
		try(Connection connection = dsConnection.getConnection();
			PreparedStatement ps = connection.prepareStatement(QUERY_INSERT)) {
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
		try(Connection connection = dsConnection.getConnection();
			PreparedStatement ps = connection.prepareStatement(QUERY_UPDATE)) {
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
		try(Connection connection = dsConnection.getConnection();
				Statement statement = connection.createStatement();	) {
	        result = statement.executeUpdate(QUERY_DELETE + id);
	    } catch (SQLException e) {
	        LOGGER.error("Unable to delete a computer in computer table",e);
	    }
		return result;
	}
	
	public int getComputerCount() {
		try(Connection connection = dsConnection.getConnection();
			PreparedStatement ps = connection.prepareStatement(QUERY_COUNT)) {
			ResultSet rs = ps.executeQuery();
	        if(rs.next()) {
	        	return rs.getInt(1);
	        }
	    } catch (SQLException e) {
	        LOGGER.error("Unable to get the computer count in computer table",e);
	    }
	    return 0; 
	}
	
	public int getComputerCountSearch(String searchedName) {
		try(Connection connection = dsConnection.getConnection();
			PreparedStatement ps = connection.prepareStatement(QUERY_COUNT_SEARCH)){
			ps.setString(1,"%"+searchedName+"%");
	        ps.setString(2,"%"+searchedName+"%");
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

