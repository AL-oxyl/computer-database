package com.oxyl.validator;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.AbstractMap;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.oxyl.dto.ComputerDTO;
import com.oxyl.exceptions.BadDateException;
import com.oxyl.exceptions.BadDateOrderException;
import com.oxyl.exceptions.NameException;
import com.oxyl.exceptions.NotANumberException;
import com.oxyl.model.Company;


@Component
public class ComputerValidator {

	private static final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MM-yyyy");
	private static final Logger LOGGER = LoggerFactory.getLogger(ComputerValidator.class);
	private static final String INTRODATE = "IntroDate";
	private static final String DISDATE = "DisDate";
	private static final String ORDERDATE = "OrderDate";
	private static final String COMPUTERNAME = "ComputerName";
	private static final String BADCOMPANYID = "BadCompanyID";
	
	public static boolean checkNullableEntry(String ...stringToVerify) {
		return Arrays.stream(stringToVerify).anyMatch(x -> x == null);
	}
	
	public static boolean checkValidId(String manufacturer) throws NotANumberException {
		try {
			int id = Integer.parseInt(manufacturer);
			return id >= 0;
		} catch (NumberFormatException e) {
			LOGGER.error("Unable to parse manufacturer id:" + manufacturer);
			throw new NotANumberException("Unable to parse manufacturer id:" + manufacturer);
		}
	}

	private static boolean checkName(String name) throws NameException {
		if (name.length() > 255) throw new NameException("La taille est trop grande, merci de la diminuer");
		if(name.length() < 0) throw new NameException("Ce nom ne peut exister");
		if(name.length() == 0) throw new NameException("L'ajout d'un nom est obligatoire, merci de compléter le champ");
		return true;
	}

	private static Optional<LocalDate> checkOnceDate(String introDate) throws BadDateException {
		try {
			return Optional.of(LocalDate.parse(introDate, dtf));
		} catch (DateTimeParseException e){
			throw new BadDateException("La date " + introDate + "n'est pas au format valide");
		}
	}

	private static boolean checkManufacturer(String manufacturer) throws NotANumberException {
		if("".equals(manufacturer)) {
			return true;
		}
		try {
			int id = Integer.parseInt(manufacturer);
			return id >= 0;
		} catch (NumberFormatException e) {
			LOGGER.error("Unable to parse manufacturer id:" + manufacturer);
			throw new NotANumberException("Unable to parse manufacturer id:" + manufacturer);
		}
	}

	private static boolean checkValidManufacturer(Optional<String> manufacturerId, List<Company> companies) throws NotANumberException {
		if (!manufacturerId.isPresent() || "".equals(manufacturerId.get())) {
			return true;
		}
		if (checkManufacturer(manufacturerId.get())) {
			for (Company company : companies) {
				try {
					if (Integer.parseInt(manufacturerId.get()) == company.getId()) {
						return true;
					}
				} catch (NumberFormatException e){
					LOGGER.error("Unable to parse manufacturer id:" + manufacturerId);
					throw new NotANumberException("Unable to parse manufacturer id:" + manufacturerId);
				}
			}
		}
		return false;
	}

	private static void checkDateOrder(Optional<LocalDate> intro, Optional<LocalDate> dis) throws BadDateOrderException{
		if (intro.isPresent() && dis.isPresent()) {
			if(dis.get().isBefore(intro.get())) {
				throw new BadDateOrderException();
			}
		}
	}
	

	public static Map<String,String> checkComputer(ComputerDTO dto, List<Company> companies) {
		Map<String,String> exceptionsMap = new HashMap<String,String>();
		Optional<LocalDate> intro = Optional.empty();
		Optional<LocalDate> dis = Optional.empty();
		try {
			intro = checkOnceDate(dto.getIntroductionDate());
		} catch (BadDateException exception){
			exceptionsMap.put(INTRODATE, exception.getMessage());
		}
		
		try {
			dis = checkOnceDate(dto.getDiscontinuedDate());
		} catch (BadDateException exception){
			exceptionsMap.put(DISDATE, exception.getMessage());
		}
		
		try {
			checkDateOrder(intro, dis);
		} catch (BadDateOrderException exception) {
			exceptionsMap.put(ORDERDATE, exception.getMessage());
		}
		
		try {
			checkName(dto.getComputerName());
		} catch (NameException exception) {
			exceptionsMap.put(COMPUTERNAME, exception.getMessage());
		}
		
		try {
		    checkValidManufacturer(dto.getCompanyId(), companies);
		} catch (NotANumberException exception) {
			exceptionsMap.put(BADCOMPANYID, exception.getMessage());
		}
		return exceptionsMap;
	}
}
