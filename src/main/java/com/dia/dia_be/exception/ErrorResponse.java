package com.dia.dia_be.exception;

import org.springframework.http.HttpStatus;

import lombok.Builder;

@Builder
public record ErrorResponse(HttpStatus httpStatus, int code, String message) {
	public static ErrorResponse of(HttpStatus httpStatus, int code, String message) {
		return ErrorResponse.builder()
			.httpStatus(httpStatus)
			.code(code)
			.message(message)
			.build();
	}

}
