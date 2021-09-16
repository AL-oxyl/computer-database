package com.oxyl.dao;

import java.sql.SQLException;
import java.util.List;

import com.oxyl.model.Computer;

public interface ComputerDao {
	Computer getComputer(int id) throws SQLException;
	List<Computer> getAllComputers();
	boolean insertComputer(Computer computer);
	boolean updateComputer(Computer computer);
	boolean deleteComputer(int id);
}
