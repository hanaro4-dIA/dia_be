package com.dia.dia_be.util;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class DateTime {

	// 고정된 패턴 상수
	private static final String DATE_PATTERN = "yyyy-MM-dd";
	private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern(DATE_PATTERN);

	private static final String TIME_PATTERN = "HH:mm"; // 24시간제 포맷
	private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern(TIME_PATTERN);

	/**
	 * 문자열을 LocalDate로 변환
	 *
	 * @param dateString 변환할 날짜 문자열 (형식: yyyy-MM-dd)
	 * @return 변환된 LocalDate 객체
	 * @throws IllegalArgumentException 잘못된 날짜 형식일 경우 예외 발생
	 */
	public static LocalDate stringToLocalDate(String dateString) {
		if (dateString == null) {
			throw new IllegalArgumentException("Date string must not be null");
		}

		try {
			return LocalDate.parse(dateString, DATE_FORMATTER);
		} catch (DateTimeParseException e) {
			throw new IllegalArgumentException("Invalid date format: " + dateString + ", expected format: " + DATE_PATTERN, e);
		}
	}

	/**
	 * 문자열을 LocalTime으로 변환
	 *
	 * @param timeString 변환할 시간 문자열 (형식: HH:mm)
	 * @return 변환된 LocalTime 객체
	 * @throws IllegalArgumentException 잘못된 시간 형식일 경우 예외 발생
	 */
	public static LocalTime stringToLocalTime(String timeString) {
		if (timeString == null) {
			throw new IllegalArgumentException("Time string must not be null");
		}

		try {
			return LocalTime.parse(timeString, TIME_FORMATTER);
		} catch (DateTimeParseException e) {
			throw new IllegalArgumentException("Invalid time format: " + timeString + ", expected format: " + TIME_PATTERN, e);
		}
	}
}
