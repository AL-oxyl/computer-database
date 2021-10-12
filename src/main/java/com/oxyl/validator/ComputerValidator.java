package com.oxyl.validator;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.oxyl.dto.ComputerDTO;
import com.oxyl.model.Company;

public class ComputerValidator {

	private static final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MMM-yyyy");
	private static final Logger LOGGER = LoggerFactory.getLogger(ComputerValidator.class);

	public static boolean checkName(String name) {
		if (name.length() < 255 & name.length() > 0) {
			return true;
		}
		return false;
	}

	public static Optional<LocalDateTime> checkOnceDate(String introDate) throws NullPointerException {
		try {
			return Optional.of(LocalDateTime.parse(introDate, dtf));

		} catch (DateTimeParseException e) {
			LOGGER.error("Unable to parse date:" + introDate);
		}
		return Optional.empty();
	}

	public static boolean checkManufacturer(String manufacturer) {
		if("".equals(manufacturer)) {
			return true;
		}
		try {
			int id = Integer.parseInt(manufacturer);
			return id >= 0;
		} catch (NumberFormatException e) {
			LOGGER.error("Unable to parse manufacturer id:" + manufacturer);
			return false;
		}
	}

	public static boolean checkValidManufacturer(Optional<String> manufacturerId, List<Company> companies) {
		if (!manufacturerId.isPresent() || "".equals(manufacturerId.get())) {
			return true;
		}
		if (checkManufacturer(manufacturerId.get())) {
			for (Company company : companies) {
				if (Integer.parseInt(manufacturerId.get()) == company.getId()) {
					return true;
				}
			}
		}
		return false;
	}

	public static boolean checkDate(String introDate, String disDate) {
		Optional<LocalDateTime> intro = checkOnceDate(introDate);
		if (intro.isPresent()) {
			Optional<LocalDateTime> dis = checkOnceDate(disDate);
			if (dis.isPresent()) {
				return intro.get().isBefore(dis.get());
			}

		}
		return false;
	}

	public static boolean checkValidDate(String introDate, String disDate) {
		if(!"".equals(introDate) && !"".equals(disDate)) {
			return checkDate(introDate,disDate);
			
		} else {
			return (("".equals(introDate) || checkOnceDate(introDate).isPresent()) &&
			       (("".equals(disDate) || checkOnceDate(disDate).isPresent())));
		}
	}

	public static boolean checkComputer(ComputerDTO dto, List<Company> companies) {
		if (!checkValidDate(dto.getIntroductionDate(), dto.getDiscontinuedDate()) || 
			!checkName(dto.getComputerName())|| 
			!checkValidManufacturer(dto.getId(), companies)) {
			return false;
		}
		return true;
	}
}
