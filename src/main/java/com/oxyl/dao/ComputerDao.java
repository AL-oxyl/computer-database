package com.oxyl.dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import com.oxyl.dto.BddComputerDTO;
import com.oxyl.model.Computer;

public interface ComputerDao {
	Optional<Computer> getComputer(int id) throws SQLException;
	List<Optional<Computer>> getAllComputers();
	void insertComputer(BddComputerDTO computerDto);
	void updateComputer(BddComputerDTO computerDto);
	void deleteComputer(int id);
}
