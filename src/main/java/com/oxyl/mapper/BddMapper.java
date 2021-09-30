package com.oxyl.mapper;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Optional;

public class BddMapper {
	
	public static Optional<Timestamp> optLocalDateToOptTimestamp(Optional<LocalDateTime> date) {
		if(date.isPresent()) {
			return Optional.of(Timestamp.valueOf(date.get()));
		} 
		return Optional.empty();
	}
	
	public static Optional<LocalDateTime> optTimestampToOptLocalDate (Optional<Timestamp> date) {
		if(date.isPresent()) {
			return Optional.of(date.get().toLocalDateTime().atZone(ZoneId.of("Europe/Paris")).toLocalDateTime());
		} 
		return Optional.empty();
	}
}