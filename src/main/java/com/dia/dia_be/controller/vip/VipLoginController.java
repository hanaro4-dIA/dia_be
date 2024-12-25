package com.dia.dia_be.controller.vip;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dia.dia_be.domain.Customer;
import com.dia.dia_be.domain.VipSessionConst;
import com.dia.dia_be.dto.vip.loginDTO.RequestVipSignUpDTO;
import com.dia.dia_be.dto.vip.loginDTO.VipLoginDTO;
import com.dia.dia_be.dto.vip.loginDTO.VipLoginForm;
import com.dia.dia_be.global.session.SessionManager;
import com.dia.dia_be.service.vip.intf.VipLoginService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("vip")
@Tag(name = "사용자 인증 및 계정 관리", description = "회원가입, 로그인, 로그아웃 관련 API 모음")
public class VipLoginController {
	private final VipLoginService vipLoginService;
	private final SessionManager sessionManager;

	public VipLoginController(VipLoginService vipLoginService, SessionManager sessionManager) {
		this.vipLoginService = vipLoginService;
		this.sessionManager = sessionManager;
	}

	@PostMapping("/login")
	@Operation(summary = "vip 회원 확인", description = "vip login")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "회원 로그인", content = @Content(mediaType = "application/json")),
		@ApiResponse(responseCode = "500", description = "회원 로그인 실패")
	})
	public ResponseEntity<VipLoginDTO> checkLogin(@RequestBody VipLoginForm vipLoginForm, HttpServletRequest request,
		HttpServletResponse response) {
		Customer customer = vipLoginService.checkLogin(vipLoginForm);

		VipLoginDTO loginDTO = new VipLoginDTO(customer.getId());
		HttpSession session = request.getSession(true);
		session.setAttribute(VipSessionConst.LOGIN_VIP, loginDTO);
		sessionManager.createSession(session, response);
		return ResponseEntity.ok().body(loginDTO);
	}

	@PostMapping("/logout")
	@Operation(summary = "VIP 로그아웃")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "로그아웃 성공", content = @Content(mediaType = "application/json")),
		@ApiResponse(responseCode = "500", description = "로그아웃 실패")
	})
	public ResponseEntity<String> logout(HttpServletRequest request) {
		HttpSession session = sessionManager.getSession(request);
		session.removeAttribute(VipSessionConst.LOGIN_VIP);
		session.invalidate(); //관련된 모든 session 속성 삭제
		sessionManager.destroySession(session);
		return ResponseEntity.ok().body("logout success");
	}

	@PostMapping("/signup")
	@Operation(summary = "VIP 회원 가입")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "회원 가입 성공", content = @Content(mediaType = "application/json")),
		@ApiResponse(responseCode = "500", description = "회원 가입 실패")
	})
	public ResponseEntity<Long> signup(@RequestBody RequestVipSignUpDTO requestVipSignUpDTO) {
		return ResponseEntity.ok().body(vipLoginService.signupProcess(requestVipSignUpDTO));
	}
}
