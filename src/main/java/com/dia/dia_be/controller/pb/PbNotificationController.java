package com.dia.dia_be.controller.pb;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dia.dia_be.domain.PbSessionConst;
import com.dia.dia_be.dto.pb.loginDTO.LoginDTO;
import com.dia.dia_be.dto.pb.notificationDTO.RequestNotificationDTO;
import com.dia.dia_be.dto.pb.notificationDTO.ResponseNotificationDTO;
import com.dia.dia_be.service.pb.intf.PbNotificationService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/pb/notifications")
@Tag(name = "PbNotification", description = "Notification API")
public class PbNotificationController {

	private final PbNotificationService pbNotificationService;

	public PbNotificationController(PbNotificationService pbNotificationService) {
		this.pbNotificationService = pbNotificationService;
	}

	// {{base_url}}/pb/notifications
	// GET - 이전에 전송한 쪽지 리스트 조회 (전체)
	@GetMapping("")
	@Operation(summary = "전체 쪽지 리스트 조회", description = "이전에 전송된 모든 쪽지 리스트를 조회합니다.")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "쪽지 리스트 조회 성공", content = @Content(mediaType = "application/json")),
		@ApiResponse(responseCode = "500", description = "서버 오류")
	})
	public ResponseEntity<List<ResponseNotificationDTO>> getAllNotifications(HttpServletRequest request) {
		// 세션 확인 코드 추가
		HttpSession session = request.getSession(false);
		if (session == null) { // 세션이 없으면 홈으로 이동
			return new ResponseEntity<>(null, HttpStatus.FOUND);
		}

		LoginDTO loginDTO =  (LoginDTO) session.getAttribute(PbSessionConst.LOGIN_PB);
		if (loginDTO == null) { // 세션에 회원 데이터가 없으면 홈으로 이동
			return new ResponseEntity<>(null, HttpStatus.FOUND);
		}

		return ResponseEntity.ok(pbNotificationService.getAllNotifications());
	}

	// {{base_url}}/pb/notifications/search?id={{customerIds}}
	// {{base_url}}/pb/notifications/search?id=1&id=2&id=3
	// GET - 특정 손님에게 보낸 쪽지 조회
	@GetMapping("/search")
	@Operation(summary = "특정 고객들 쪽지 조회", description = "특정 고객 ID들에 대해 보낸 쪽지를 조회합니다.")
	@Parameters({
		@Parameter(name = "id", description = "조회할 고객들 ID 목록", example = "1, 2, 3")
	})
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "쪽지 조회 성공", content = @Content(mediaType = "application/json")),
		@ApiResponse(responseCode = "400", description = "잘못된 요청"),
		@ApiResponse(responseCode = "404", description = "쪽지가 존재하지 않음")
	})
	public ResponseEntity<List<ResponseNotificationDTO>> getNotificationsByCustomerIds(
		@RequestParam(name = "id") List<Long> customerIds, HttpServletRequest request) {
		// 세션 확인 코드 추가
		HttpSession session = request.getSession(false);
		if (session == null) { // 세션이 없으면 홈으로 이동
			return new ResponseEntity<>(null, HttpStatus.FOUND);
		}

		LoginDTO loginDTO =  (LoginDTO) session.getAttribute(PbSessionConst.LOGIN_PB);
		if (loginDTO == null) { // 세션에 회원 데이터가 없으면 홈으로 이동
			return new ResponseEntity<>(null, HttpStatus.FOUND);
		}

		return ResponseEntity.ok(pbNotificationService.getNotificationsByCustomerIds(customerIds));
	}

	// {{base_url}}/pb/notifications/{{NotificationId}}
	// GET - 쪽지 자세히 보기
	@GetMapping("/{NotificationId}")
	@Operation(summary = "쪽지 상세 조회", description = "특정 쪽지의 상세 정보를 조회합니다.")
	@Parameter(name = "NotificationId", description = "조회할 쪽지의 ID", example = "1")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "쪽지 조회 성공", content = @Content(mediaType = "application/json")),
		@ApiResponse(responseCode = "404", description = "쪽지가 존재하지 않음")
	})
	public ResponseEntity<ResponseNotificationDTO> getNotificationById(@PathVariable Long NotificationId, HttpServletRequest request) {
		// 세션 확인 코드 추가
		HttpSession session = request.getSession(false);
		if (session == null) { // 세션이 없으면 홈으로 이동
			return new ResponseEntity<>(null, HttpStatus.FOUND);
		}

		LoginDTO loginDTO =  (LoginDTO) session.getAttribute(PbSessionConst.LOGIN_PB);
		if (loginDTO == null) { // 세션에 회원 데이터가 없으면 홈으로 이동
			return new ResponseEntity<>(null, HttpStatus.FOUND);
		}

		return ResponseEntity.ok(pbNotificationService.getNotificationById(NotificationId));
	}

	// {{base_url}}/pb/notifications/send
	// Body - raw로 JSON 전송
	// POST - 여러 손님에게 쪽지 전송
	// {{base_url}}/pb/notifications/send?customerIds=1&customerIds=2
	@PostMapping("/send")
	@Operation(summary = "쪽지 전송", description = "여러 고객에게 쪽지를 전송합니다.")
	@Parameters({
		@Parameter(name = "customerIds", description = "쪽지를 보낼 고객 ID 목록", example = "1, 2")
	})
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "쪽지 전송 성공", content = @Content(mediaType = "application/json")),
		@ApiResponse(responseCode = "400", description = "잘못된 요청"),
		@ApiResponse(responseCode = "404", description = "고객이 존재하지 않음")
	})
	public ResponseEntity<List<ResponseNotificationDTO>> sendNotifications(
		@RequestParam List<Long> customerIds,
		@RequestBody RequestNotificationDTO notificationRequest, HttpServletRequest request) {
		// 세션 확인 코드 추가
		HttpSession session = request.getSession(false);
		if (session == null) { // 세션이 없으면 홈으로 이동
			return new ResponseEntity<>(null, HttpStatus.FOUND);
		}

		LoginDTO loginDTO =  (LoginDTO) session.getAttribute(PbSessionConst.LOGIN_PB);
		if (loginDTO == null) { // 세션에 회원 데이터가 없으면 홈으로 이동
			return new ResponseEntity<>(null, HttpStatus.FOUND);
		}

		notificationRequest.setCustomerIds(customerIds);
		List<ResponseNotificationDTO> sentNotifications = pbNotificationService.sendNotifications(notificationRequest);
		return ResponseEntity.ok(sentNotifications);
	}

}
