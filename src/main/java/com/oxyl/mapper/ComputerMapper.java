package com.oxyl.mapper;


import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.oxyl.dto.ComputerDTO;
import com.oxyl.model.Computer;

public class ComputerMapper {
	private static ComputerMapper instance;
	private static final Logger LOGGER = LoggerFactory.getLogger(ComputerMapper.class);
	
	private ComputerMapper() {
		LOGGER.info("Computer mapper instantiate");
	}

	public static ComputerMapper getInstance() {
		if (instance == null) {
			instance = new ComputerMapper();
		}
		return instance;
	}
	
	public static ComputerDTO computerModelToComputerDTO(Computer model) {
		return new ComputerDTO(model);
	}
	
	public static ArrayList<ComputerDTO> computerListToDTOList(ArrayList<Computer> computerList) {
		ArrayList<ComputerDTO> dtoList = new ArrayList<ComputerDTO>();
		for(Computer computer : computerList) {
			dtoList.add(computerModelToComputerDTO(computer));
		}
		return dtoList;
	}
}
