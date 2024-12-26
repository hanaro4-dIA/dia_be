package com.dia.dia_be.controller.vip;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dia.dia_be.domain.VipSessionConst;
import com.dia.dia_be.dto.vip.loginDTO.VipLoginDTO;
import com.dia.dia_be.global.session.SessionManager;
import com.dia.dia_be.service.vip.intf.VipNotificationService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@RestController
@Tag(name = "VIP - 알림", description = "알림 load, read, delete")
@RequestMapping("/vip/notifications")
public class VipNotificationController {

	private final VipNotificationService vipNotificationService;
	private final SessionManager sessionManager;

	public VipNotificationController(VipNotificationService vipNotificationService, SessionManager sessionManager) {
		this.vipNotificationService = vipNotificationService;
		this.sessionManager = sessionManager;
	}

	@GetMapping
	@Operation(summary = "특정 VIP의 알림 내용을 가져오는 API")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "알림 리스트 반환 성공", content = @Content(mediaType = "application/json")),
		@ApiResponse(responseCode = "500", description = "잘못된 요청으로 인한 반환 실패")
	})
	public ResponseEntity<?> getNotifications(HttpServletRequest request) {
		HttpSession session = sessionManager.getSession(request);
		if (session == null) {
			return new ResponseEntity<>("No session found", HttpStatus.FOUND);
		}

		// 세션에서 VIP 로그인 정보 가져오기
		VipLoginDTO loginDTO = (VipLoginDTO)session.getAttribute(VipSessionConst.LOGIN_VIP);
		if (loginDTO == null) {
			return new ResponseEntity<>("Can't find user data in session", HttpStatus.FOUND);
		}

		try {
			return ResponseEntity.ok(vipNotificationService.getNotifications(loginDTO.getCustomerId()));
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@DeleteMapping
	@Operation(summary = "특정 VIP의 모든 알림 삭제 API")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "삭제 성공"),
		@ApiResponse(responseCode = "500", description = "삭제 실패")
	})
	public ResponseEntity<String> deleteAllNotifications(HttpServletRequest request) {
		HttpSession session = sessionManager.getSession(request);
		if (session == null) {
			return new ResponseEntity<>("No session found", HttpStatus.FOUND);
		}

		// 세션에서 VIP 로그인 정보 가져오기
		VipLoginDTO loginDTO = (VipLoginDTO)session.getAttribute(VipSessionConst.LOGIN_VIP);
		if (loginDTO == null) {
			return new ResponseEntity<>("Can't find user data in session", HttpStatus.FOUND);
		}

		try {
			vipNotificationService.deleteAllNotifications(loginDTO.getCustomerId());
			return ResponseEntity.ok().body("전체 알림이 삭제되었습니다.");
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@PatchMapping
	@Operation(summary = "특정 VIP의 모든 알림 읽음 처리 API")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "읽음 처리 성공"),
		@ApiResponse(responseCode = "500", description = "읽음 처리 실패")
	})
	public ResponseEntity<String> markAllNotificationsAsRead(HttpServletRequest request) {
		HttpSession session = sessionManager.getSession(request);
		if (session == null) {
			return new ResponseEntity<>("No session found", HttpStatus.FOUND);
		}

		// 세션에서 VIP 로그인 정보 가져오기
		VipLoginDTO loginDTO = (VipLoginDTO)session.getAttribute(VipSessionConst.LOGIN_VIP);
		if (loginDTO == null) {
			return new ResponseEntity<>("Can't find user data in session", HttpStatus.FOUND);
		}

		try {
			vipNotificationService.markAllNotificationsAsRead(loginDTO.getCustomerId());
			return ResponseEntity.ok().body("전체 알림이 읽음 처리되었습니다.");
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
}
