package com.dia.dia_be.exception;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum CommonErrorCode implements ErrorCode {
	// 400
	BAD_REQUEST(HttpStatus.BAD_REQUEST, 400, "잘못된 요청입니다."),
	NULL_POINTER_ERROR(HttpStatus.BAD_REQUEST, 400, "필수 값이 누락되었습니다."),
	VALIDATION_ERROR(HttpStatus.BAD_REQUEST, 400, "유효성 검사 실패"),
	METHOD_NOT_ALLOWED(HttpStatus.BAD_REQUEST, 400, "허용되지 않은 HTTP 메서드입니다."),

	// 500
	INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, 500, "서버 오류가 발생했습니다. 잠시 후 다시 시도해주세요."),
	ILLEGAL_STATE_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, 500, "서버 상태가 올바르지 않습니다.");

	private final HttpStatus httpStatus;
	private final int code;
	private final String message;

}
