package com.oxyl.service;


import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.oxyl.dao.ComputerDAO;
import com.oxyl.model.Computer;

@Service
public class ComputerService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ComputerService.class);
	
	public static Optional<Computer> getComputer(int id) {
		LOGGER.info("Je suis passé par là");
		ComputerDAO computers = ComputerDAO.getInstance();
		Optional<Computer> computer = computers.getComputer(id);
		return computer;
	}
	
	public static void addComputer(Computer computer) {
		ComputerDAO computers = ComputerDAO.getInstance();
		int result = computers.insertComputer(computer);
		int numberComputer = ComputerPageHandlerStrategyService.getNumberComputer();
		ComputerPageHandlerStrategyService.setNumberComputer(numberComputer + result);
	}
	
	public static boolean updateComputer(Computer computer) {
		ComputerDAO computers = ComputerDAO.getInstance();
		boolean result = computers.updateComputer(computer);
		return result;
	}
	
	public static void deleteComputer(int id) {
		ComputerDAO computers = ComputerDAO.getInstance();
		int result = computers.deleteComputer(id);
		int numberComputer = ComputerPageHandlerStrategyService.getNumberComputer();
		ComputerPageHandlerStrategyService.setNumberComputer(numberComputer - result);
	}
}
