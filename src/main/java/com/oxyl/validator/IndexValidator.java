package com.oxyl.validator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.oxyl.service.ComputerPageHandlerStrategyService;

@Component
public class IndexValidator {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(IndexValidator.class);
	
	public IndexValidator() {}
	
	public boolean indexValidator(int index, int numberPage) {
		if(index < 0) {
			LOGGER.error("Index négatif interdit");
		} else if (index >= numberPage) {
			LOGGER.error("Index supérieur au nombre de page total");
		} else {
			return true;
		}
		return false;
	}
}
