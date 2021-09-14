package com.oxyl.dao;

import java.util.List;

import com.oxyl.model.Computer;

public interface ComputerDao {
	Computer getComputer(int id);
	List<Computer> getAllComputers();
	boolean insertComputer(Computer computer);
	boolean updateComputer(Computer computer);
	boolean deleteComputer(int id);
}
