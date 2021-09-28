package com.oxyl.dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import com.oxyl.model.Computer;

public interface ComputerDao {
	Optional<Computer> getComputer(int id) throws SQLException;
	List<Computer> getAllComputers();
	boolean insertComputer(Computer computer);
	boolean updateComputer(Computer computer);
	boolean deleteComputer(int id);
}
