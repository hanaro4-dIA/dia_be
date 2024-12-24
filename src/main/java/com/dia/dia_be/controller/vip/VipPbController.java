package com.dia.dia_be.controller.vip;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dia.dia_be.domain.VipSessionConst;
import com.dia.dia_be.dto.vip.loginDTO.VipLoginDTO;
import com.dia.dia_be.global.session.SessionManager;
import com.dia.dia_be.service.vip.intf.VipPbService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/vip/pb")
@Slf4j
@Tag(name = "VIP - 기타 조회 관련 API", description = "PB 프로필, 카테고리 목록")
public class VipPbController {

	private final VipPbService vipPbService;
	private final SessionManager sessionManager;

	public VipPbController(VipPbService vipPbService, SessionManager sessionManager) {
		this.vipPbService = vipPbService;
		this.sessionManager = sessionManager;
	}

	@GetMapping
	@Operation(summary = "PB 프로필 조회", description = "특정 손님의 PB 프로필 정보를 조회합니다.")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "PB 프로필 조회 성공", content = @Content(mediaType = "application/json")),
		@ApiResponse(responseCode = "500", description = "잘못된 요청으로 인한 반환 실패")
	})
	public ResponseEntity<?> getPbProfile(HttpServletRequest request) {

		// // 세션 토큰을 사용해 세션을 가져옵니다.
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
			// 로그인된 회원 정보로 프로필을 조회하여 반환
			return ResponseEntity.ok(vipPbService.getPbProfile(loginDTO.getCustomerId()));
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
}
