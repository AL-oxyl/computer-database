package com.oxyl.mapper;

import java.sql.Timestamp;


import java.time.LocalDate;
//import java.time.ZoneId;
import java.util.Optional;

import org.springframework.stereotype.Component;

import com.oxyl.dto.BddComputerDTO;
import com.oxyl.model.Company;
import com.oxyl.model.Computer;

@Component
public class BddMapper {
	
	public static Optional<Timestamp> optLocalDateToOptTimestamp(Optional<LocalDate> date) {
		if(date.isPresent()) {
			return Optional.of(Timestamp.valueOf(date.get().atStartOfDay()));
		} 
		return Optional.empty();
	}
	
	public static Optional<LocalDate> optTimestampToOptLocalDate (Optional<Timestamp> date) {
		if(date.isPresent()) {
			return Optional.of(date.get().toLocalDateTime().toLocalDate()); // .atZone(ZoneId.of("Europe/Paris")).toLocalDateTime()
		} 
		return Optional.empty();
	}
	
	public static BddComputerDTO computerModelToComputerBdd(Computer computer) {
		int computerId = computer.getId();
		Optional<Timestamp> introDate = BddMapper.optLocalDateToOptTimestamp(computer.getIntroductionDate());
		Optional<Timestamp> disDate = BddMapper.optLocalDateToOptTimestamp(computer.getDiscontinuedDate());
		Optional<String> computerName = Optional.of(computer.getComputerName());
		Optional<Integer> companyId = computer.getManufacturer().map(Company::getId);
		return new BddComputerDTO(computerId,computerName,introDate,disDate,companyId);
	}
}