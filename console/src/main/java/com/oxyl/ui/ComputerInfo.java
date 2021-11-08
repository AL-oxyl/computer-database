package com.oxyl.ui;

import com.oxyl.model.Computer;

public class ComputerInfo {
	private Computer computer;
	private static final String newLine = System.getProperty("line.separator");
	
	public ComputerInfo(Computer computer) {
		this.computer = computer;
	}
	
	public void show() {
		StringBuilder computerInfo = new StringBuilder();
		computerInfo.append("Voilà les informations de l'ordinateur: ").append(computer.getId());
		computerInfo.append(newLine);
		if(computer.getComputerName()!=null) {
			computerInfo.append("Nom de l'ordinateur : ").append(computer.getComputerName());
			computerInfo.append(newLine);
		}
		if(computer.getIntroductionDate()!=null) {
			computerInfo.append("Date d'ajout de l'ordinateur : ").append(computer.getIntroductionDate());
			computerInfo.append(newLine);
		}
		if(computer.getDiscontinuedDate()!=null) {
			computerInfo.append("Date d'écart de l'ordinateur : ").append(computer.getDiscontinuedDate());
			computerInfo.append(newLine);
		}
		if(computer.getManufacturer()!=null) {
			computerInfo.append("Constructeur de l'ordinateur : ").append(computer.getManufacturer());
		}
		System.out.println(computerInfo);
	}
}
