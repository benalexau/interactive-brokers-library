package com.blogspot.mikelaud.ibl.types;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Use the format:
 * yyyymmdd hh:mm:ss tmz
 * where the time zone is allowed (optionally) after a space at the end
 * (see reqHistoricalData()).
 */
public class IblEndDateTime implements Comparable<IblEndDateTime> {
	
	private static final String PATTERN = "yyyyMMdd HH:mm:ss VV";
	private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern(PATTERN);

	private final ZonedDateTime DATE_TIME;

	public ZonedDateTime get() {
		return DATE_TIME;
	}
	
	public String getValue() {
		return FORMATTER.format(DATE_TIME);
	}

	@Override
	public int compareTo(IblEndDateTime aOther) {
		return DATE_TIME.compareTo(aOther.DATE_TIME);
	}

	@Override
	public String toString() {
		return getValue();
	}
	
	public IblEndDateTime(LocalDateTime aLocalDateTime, ZoneId aZoneId) {
		DATE_TIME = ZonedDateTime.of(aLocalDateTime, aZoneId);
	}
	
}
