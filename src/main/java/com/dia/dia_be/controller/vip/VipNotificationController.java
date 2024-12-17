package com.dia.dia_be.controller.vip;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dia.dia_be.service.vip.intf.VipNotificationService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@Tag(name = "VipNotification", description = "Notification API")
@RequestMapping("/vip/notifications")
public class VipNotificationController {

	private final VipNotificationService vipNotificationService;

	public VipNotificationController(VipNotificationService vipNotificationService) {
		this.vipNotificationService = vipNotificationService;
	}

	// GET {{base_url}}/vip/notifications
	// 해당 customer(=VIP)의 모든 알림을 가져옴
	@GetMapping
	@Operation(summary = "특정 VIP의 알림 내용을 가져오는 API")
	@Parameters({
		@Parameter(name = "id", description = "customerId", example = "1")
		// @Parameter(name = "title", description = "알림 제목", example = "제목1"),
		// @Parameter(name = "text", description = "알림 내용", example = "내용1"),
		// @Parameter(name = "isRead", description = "읽음 여부", example = "false")
	})
	public ResponseEntity<?> findByCustomerId() {
		final Long customerId = 1L;
		try {
			return ResponseEntity.ok(vipNotificationService.getNotifications(customerId));
		} catch (Exception e) {
			return ResponseEntity.status(500).body(e.getMessage());
		}
	}


	// GET {{base_url}}/vip/notifications/{customerId}
	// 해당 customerId의 전체 알림 삭제
	@DeleteMapping("/{customerId}")
	@Operation(summary = "특정 VIP의 모든 알림 삭제 API")
	@Parameters({
		@Parameter(name = "customerId", description = "Customer의 고유 ID", example = "1")
	})
	public ResponseEntity<?> deleteAllNotifications(@PathVariable("customerId") Long customerId) {
		vipNotificationService.deleteAllNotifications(customerId);
		return ResponseEntity.ok().body("전체 알림이 삭제되었습니다.");
	}

	// GET {{base_url}}/vip/notifications/{customerId}/read
	// 해당 customerId의 전체 알림 읽음 처리
	@PatchMapping("/{customerId}/read")
	@Operation(summary = "특정 VIP의 모든 알림 읽음 처리 API")
	@Parameters({
		@Parameter(name = "customerId", description = "Customer의 고유 ID", example = "1")
	})
	public ResponseEntity<?> markAllNotificationsAsRead(@PathVariable("customerId") Long customerId) {
		vipNotificationService.markAllNotificationsAsRead(customerId);
		return ResponseEntity.ok().body("전체 알림이 읽음 처리되었습니다.");
	}
}
