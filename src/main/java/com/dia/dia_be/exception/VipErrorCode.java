package com.dia.dia_be.exception;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum VipErrorCode implements ErrorCode {

	// 404
	PB_PROFILE_NOT_FOUND(HttpStatus.NOT_FOUND, 404, "PB 프로필을 찾을 수 없습니다."),
	RESERVE_NOT_FOUND(HttpStatus.NOT_FOUND, 404, "상담 예약을 찾을 수 없습니다."),
	JOURNAL_NOT_FOUND(HttpStatus.NOT_FOUND, 404, "상담 내역을 찾을 수 없습니다."),
	NOTIFICATION_NOT_FOUND(HttpStatus.NOT_FOUND, 404, "알림을 찾을 수 없습니다."),
	CUSTOMER_NOT_FOUND(HttpStatus.NOT_FOUND, 404, "VIP를 찾을 수 없습니다."),

	// 400
	INVALID_RESERVE_REQUEST(HttpStatus.BAD_REQUEST, 400, "상담 예약 요청이 유효하지 않습니다."),
	INVALID_FAST_RESERVE_REQUEST(HttpStatus.BAD_REQUEST, 400, "빠른 상담 요청이 유효하지 않습니다."),
	INVALID_SIGNUP_REQUEST(HttpStatus.BAD_REQUEST, 400, "회원가입 요청이 유효하지 않습니다."),

	// 403
	ACCESS_DENIED(HttpStatus.FORBIDDEN, 403, "VIP에게 권한이 없습니다."),

	// 500
	RESERVE_CREATION_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, 500, "상담 예약 생성에 실패했습니다."),
	FAST_RESERVE_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, 500, "빠른 상담 요청에 실패했습니다."),
	NOTIFICATION_DELETE_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, 500, "알림 삭제에 실패했습니다.");

	private final HttpStatus httpStatus;
	private final int code;
	private final String message;

}
