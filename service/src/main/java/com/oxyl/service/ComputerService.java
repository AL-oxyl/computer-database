package com.oxyl.service;


import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.oxyl.dao.ComputerDAO;
import com.oxyl.mapper.BddMapper;
import com.oxyl.model.Computer;

@Service
public class ComputerService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ComputerService.class);
	
	private ComputerDAO computerDao;
	
	@Autowired
	public ComputerService(ComputerDAO computerDao) {
		this.computerDao = computerDao;
	}
	
	public Optional<Computer> getComputer(int id) {
		LOGGER.info("Je suis passé par là");
		Optional<Computer> computer = BddMapper.computerPersistDtoToComputerModel(computerDao.getComputer(id));
		return computer;
	}
	
	public void addComputer(Computer computer) {
		computerDao.insertComputer(computer);
	}
	
	public void updateComputer(Computer computer) {
		computerDao.updateComputer(computer);
	}
	
	public void deleteComputer(int id) {
		computerDao.deleteComputer(id);
	}
}
