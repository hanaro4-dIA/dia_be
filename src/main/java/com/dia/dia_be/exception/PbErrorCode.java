package com.dia.dia_be.exception;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum PbErrorCode implements ErrorCode {

	// 404
	PROFILE_NOT_FOUND(HttpStatus.NOT_FOUND, 404, "PB 프로필을 찾을 수 없습니다."),
	RESERVE_NOT_FOUND(HttpStatus.NOT_FOUND, 404, "상담 예약을 찾을 수 없습니다."),
	CUSTOMER_NOT_FOUND(HttpStatus.NOT_FOUND, 404, "손님 정보를 찾을 수 없습니다."),
	JOURNAL_NOT_FOUND(HttpStatus.NOT_FOUND, 404, "상담 일지를 찾을 수 없습니다."),
	NOTIFICATION_NOT_FOUND(HttpStatus.NOT_FOUND, 404, "쪽지를 찾을 수 없습니다."),

	// 400
	INVALID_PROFILE_UPDATE(HttpStatus.BAD_REQUEST, 400, "프로필 수정 요청이 유효하지 않습니다."),
	INVALID_RESERVE_APPROVAL(HttpStatus.BAD_REQUEST, 400, "상담 예약 승인 요청이 유효하지 않습니다."),
	INVALID_CUSTOMER_SEARCH(HttpStatus.BAD_REQUEST, 400, "손님 검색 요청이 유효하지 않습니다."),
	INVALID_JOURNAL_SAVE(HttpStatus.BAD_REQUEST, 400, "상담 일지 저장 요청이 유효하지 않습니다."),
	PB_AVAILABILITY_REQUEST_DTO_NULL(HttpStatus.BAD_REQUEST, 400, "요청 데이터가 비어 있습니다."),

	// 403
	ACCESS_DENIED(HttpStatus.FORBIDDEN, 403, "PB에게 권한이 없습니다."),

	// 500
	PROFILE_UPDATE_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, 500, "프로필 수정에 실패했습니다."),
	RESERVE_APPROVAL_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, 500, "상담 예약 승인에 실패했습니다."),
	JOURNAL_SAVE_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, 500, "상담 일지 저장에 실패했습니다."),
	NOTIFICATION_SEND_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, 500, "쪽지 전송에 실패했습니다."),
	RECORD_SAVE_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, 500, "녹음 파일을 가져오는데 실패했습니다.");

	private final HttpStatus httpStatus;
	private final int code;
	private final String message;
}
