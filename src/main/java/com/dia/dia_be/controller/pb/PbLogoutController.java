package com.dia.dia_be.controller.pb;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dia.dia_be.domain.PbSessionConst;
import com.dia.dia_be.dto.pb.loginDTO.LoginDTO;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("pb/logout")
@Tag(name = "사용자 인증 및 계정 관리", description = "회원가입, 로그인, 로그아웃 관련 API 모음")
public class PbLogoutController {

	@PostMapping()
	@Operation(summary = "PB 로그아웃", description = "PB 사용자가 로그아웃합니다.")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "로그아웃 성공"),
		@ApiResponse(responseCode = "400", description = "세션 없음 또는 로그인되지 않음")
	})
	public ResponseEntity<String> logout(HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		if (session == null) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No active session found");
		}

		LoginDTO loginDTO = (LoginDTO)session.getAttribute(PbSessionConst.LOGIN_PB);
		if (loginDTO == null) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No user is logged in");
		}

		//세션 무효화 -> 제거
		session.invalidate();

		return ResponseEntity.ok("Logout successful");
	}
}
