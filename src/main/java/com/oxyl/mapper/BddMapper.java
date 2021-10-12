package com.oxyl.mapper;

import java.sql.Timestamp;
import java.time.LocalDate;
//import java.time.ZoneId;
import java.util.Optional;

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
}