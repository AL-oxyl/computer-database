package com.oxyl.validator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.oxyl.service.ComputerPageHandlerStrategyService;

@Component
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
	
	public static boolean localIndexValidator(ComputerPageHandlerStrategyService pagination, int index) {
		if(index < 0) {
			LOGGER.error("Index négatif interdit");
		} else if (index >= pagination.getLocalNumberPage()) {
			LOGGER.error("Index supérieur au nombre de page total");
		} else {
			return true;
		}
		return false;
	}
}
