package com.dia.dia_be.exception;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

	//전역예외처리
	@ExceptionHandler(GlobalException.class)
	public ResponseEntity<ErrorResponse> handlePbException(GlobalException e) {
		ErrorCode errorCode = e.getErrorCode();
		ErrorResponse errorResponse = ErrorResponse.of(errorCode.getHttpStatus(), errorCode.getCode(),
			errorCode.getMessage());
		return ResponseEntity.status(errorCode.getHttpStatus()).body(errorResponse);
	}

	//NullPointerException 처리
	@ExceptionHandler(NullPointerException.class)
	public ResponseEntity<ErrorResponse> handleNullPointerException(NullPointerException e) {
		ErrorResponse errorResponse = ErrorResponse.of(
			CommonErrorCode.NULL_POINTER_ERROR.getHttpStatus(),
			CommonErrorCode.NULL_POINTER_ERROR.getCode(),
			CommonErrorCode.NULL_POINTER_ERROR.getMessage()
		);
		return ResponseEntity.status(CommonErrorCode.NULL_POINTER_ERROR.getHttpStatus()).body(errorResponse);
	}

	//IllegalArgumentException 처리
	@ExceptionHandler(IllegalArgumentException.class)
	public ResponseEntity<ErrorResponse> handleIllegalArgumentException(IllegalArgumentException e) {
		ErrorResponse errorResponse = ErrorResponse.of(
			CommonErrorCode.BAD_REQUEST.getHttpStatus(),
			CommonErrorCode.BAD_REQUEST.getCode(),
			CommonErrorCode.BAD_REQUEST.getMessage()
		);
		return ResponseEntity.status(CommonErrorCode.BAD_REQUEST.getHttpStatus()).body(errorResponse);
	}

	//IllegalStateException 처리
	@ExceptionHandler(IllegalStateException.class)
	public ResponseEntity<ErrorResponse> handleIllegalStateException(IllegalStateException e) {
		ErrorResponse errorResponse = ErrorResponse.of(
			CommonErrorCode.ILLEGAL_STATE_ERROR.getHttpStatus(),
			CommonErrorCode.ILLEGAL_STATE_ERROR.getCode(),
			CommonErrorCode.ILLEGAL_STATE_ERROR.getMessage()
		);
		return ResponseEntity.status(CommonErrorCode.ILLEGAL_STATE_ERROR.getHttpStatus()).body(errorResponse);
	}

	//MethodArgumentNotValidException 처리, 유효성 검사
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ErrorResponse> handleValidationException(MethodArgumentNotValidException e) {
		Map<String, String> errors = new HashMap<>();
		for (FieldError fieldError : e.getBindingResult().getFieldErrors()) {
			errors.put(fieldError.getField(), fieldError.getDefaultMessage());
		}
		ErrorResponse errorResponse = ErrorResponse.of(
			CommonErrorCode.VALIDATION_ERROR.getHttpStatus(),
			CommonErrorCode.VALIDATION_ERROR.getCode(),
			CommonErrorCode.VALIDATION_ERROR.getMessage() + ": " + errors
		);
		return ResponseEntity.status(CommonErrorCode.VALIDATION_ERROR.getHttpStatus()).body(errorResponse);
	}

	// HttpRequestMethodNotSupportedException 처리 (잘못된 HTTP 메서드 요청)
	@ExceptionHandler(HttpRequestMethodNotSupportedException.class)
	public ResponseEntity<ErrorResponse> handleMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {
		ErrorResponse errorResponse = ErrorResponse.of(
			CommonErrorCode.METHOD_NOT_ALLOWED.getHttpStatus(),
			CommonErrorCode.METHOD_NOT_ALLOWED.getCode(),
			CommonErrorCode.METHOD_NOT_ALLOWED.getMessage() + ": " + e.getMethod()
		);
		return ResponseEntity.status(CommonErrorCode.METHOD_NOT_ALLOWED.getHttpStatus()).body(errorResponse);
	}

	// 모든 예상치 못한 예외 처리
	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorResponse> handleGeneralException(Exception e) {
		ErrorResponse errorResponse = ErrorResponse.of(
			CommonErrorCode.INTERNAL_SERVER_ERROR.getHttpStatus(),
			CommonErrorCode.INTERNAL_SERVER_ERROR.getCode(),
			CommonErrorCode.INTERNAL_SERVER_ERROR.getMessage()
		);
		return ResponseEntity.status(CommonErrorCode.INTERNAL_SERVER_ERROR.getHttpStatus()).body(errorResponse);
	}

}
