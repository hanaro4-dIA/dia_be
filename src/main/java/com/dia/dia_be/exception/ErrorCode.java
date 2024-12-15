package com.dia.dia_be.exception;

import org.springframework.http.HttpStatus;

public interface ErrorCode {
	String name();

	HttpStatus getHttpStatus();

	int getCode();

	String getMessage();

}
