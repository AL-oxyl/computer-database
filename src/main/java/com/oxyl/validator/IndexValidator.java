package com.oxyl.validator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.oxyl.service.ComputerPageHandlerStrategyService;

public class IndexValidator {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(IndexValidator.class);
	
	public static boolean indexValidator(int index) {
		if(index < 0) {
			LOGGER.error("Index négatif interdit");
		} else if (index >= ComputerPageHandlerStrategyService.getNumberPage()) {
			LOGGER.error("Index supérieur au nombre de page total");
		} else {
			return true;
		}
		return false;
	}
}
