package com.oxyl.service;

import com.oxyl.dao.ComputerDAO;
import com.oxyl.model.Computer;

public class ComputerService {
	public static void addComputer(Computer computer) {
		ComputerDAO computers = new ComputerDAO();
		int result = computers.insertComputer(computer);
		int numberComputer = ComputerPageHandlerStrategyService.getNumberComputer();
		ComputerPageHandlerStrategyService.setNumberComputer(numberComputer + result);
	}
	
	public static boolean updateComputer(Computer computer) {
		ComputerDAO computers = new ComputerDAO();
		boolean result = computers.updateComputer(computer);
		return result;
	}
	
	public static void deleteComputer(int id) {
		ComputerDAO computers = new ComputerDAO();
		int result = computers.deleteComputer(id);
		int numberComputer = ComputerPageHandlerStrategyService.getNumberComputer();
		ComputerPageHandlerStrategyService.setNumberComputer(numberComputer - result);
	}
}
