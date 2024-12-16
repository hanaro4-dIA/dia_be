package com.dia.dia_be.controller.pb;

import com.dia.dia_be.dto.pb.NotificationDTO;
import com.dia.dia_be.service.pb.intf.NotificationService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/pb/notifications")
@Tag(name = "Notification", description = "Notification API")
public class NotificationController {

	private final NotificationService notificationService;

	public NotificationController(NotificationService notificationService) {
		this.notificationService = notificationService;
	}

	// {{base_url}}/pb/notifications
	// GET - 이전에 전송한 쪽지 리스트 조회 (전체)
	@GetMapping("")
	@Operation(summary = "전체 쪽지 리스트 조회", description = "이전에 전송된 모든 쪽지 리스트를 조회합니다.")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "쪽지 리스트 조회 성공", content = @Content(mediaType = "application/json")),
		@ApiResponse(responseCode = "500", description = "서버 오류")
	})
	public ResponseEntity<List<NotificationDTO>> getAllNotifications() {
		return ResponseEntity.ok(notificationService.getAllNotifications());
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
	public ResponseEntity<List<NotificationDTO>> getNotificationsByCustomerIds(@RequestParam(name = "id") List<Long> customerIds) {
		return ResponseEntity.ok(notificationService.getNotificationsByCustomerIds(customerIds));
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
	public ResponseEntity<NotificationDTO> getNotificationById(@PathVariable Long NotificationId) {
		return ResponseEntity.ok(notificationService.getNotificationById(NotificationId));
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
	public ResponseEntity<List<NotificationDTO>> sendNotifications(
		@RequestParam List<Long> customerIds,
		@RequestBody NotificationDTO notificationRequest) {

		notificationRequest.setCustomerIds(customerIds);
		List<NotificationDTO> sentNotifications = notificationService.sendNotifications(notificationRequest);
		return ResponseEntity.ok(sentNotifications);
	}





}
