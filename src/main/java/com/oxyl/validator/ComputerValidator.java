package com.oxyl.validator;

public class ComputerValidator {
	

	private static boolean checkName(String name) {
		return true;
	}
	
	private static boolean checkIntroductionDate(String introDate) {
		return true;
	}
	
	private static boolean checkDiscontinuedDate(String disDate) {
		return true;
	}
	
	private static boolean checkManufacturer(String manufacturer) {
		return true;
	}
	
	private static boolean checkDate(String introDate, String disDate) {
		if(checkIntroductionDate(introDate) && checkDiscontinuedDate(disDate)) {
			if(Integer.parseInt(disDate) < Integer.parseInt(disDate)) {
				return true;
			}
		}
		return false;
	}
 }
