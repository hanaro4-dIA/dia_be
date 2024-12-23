package com.dia.dia_be.controller.vip;

import org.apache.catalina.Session;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dia.dia_be.domain.Customer;
import com.dia.dia_be.domain.VipSessionConst;
import com.dia.dia_be.dto.vip.loginDTO.RequestVipSignUpDTO;
import com.dia.dia_be.dto.vip.loginDTO.VipLoginDTO;
import com.dia.dia_be.global.session.SessionManager;
import com.dia.dia_be.service.vip.intf.VipLoginService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;


import com.dia.dia_be.dto.vip.loginDTO.VipLoginForm;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("vip")
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
	public ResponseEntity<VipLoginDTO> checkLogin(@RequestBody VipLoginForm vipLoginForm, HttpServletRequest request, HttpServletResponse response){
		Customer customer = vipLoginService.checkLogin(vipLoginForm);

		VipLoginDTO loginDTO = new VipLoginDTO(customer.getId());
		HttpSession session = request.getSession(true);
		session.setAttribute(VipSessionConst.LOGIN_VIP, loginDTO);
		sessionManager.sessionCreated(session);
		return ResponseEntity.ok().body(loginDTO);
	}


	@PostMapping("/logout")
	public ResponseEntity<String> logout(HttpServletRequest request){
		HttpSession session = sessionManager.getSession(request);
		log.info("여긴가"+session);
		session.removeAttribute(VipSessionConst.LOGIN_VIP);
		log.info("여기?");
		session.invalidate(); //관련된 모든 session 속성 삭제
		sessionManager.sessionDestroyed(session);
		log.info("여기?11");
		return ResponseEntity.ok().body("logout success");
	}

	@PostMapping("/signup")
	@Tag(name = "VIP 회원 가입")
	@Operation(summary = "VIP 회원 가입", description = "VIP 회원 가입 API")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "회원 가입 성공", content = @Content(mediaType = "application/json")),
		@ApiResponse(responseCode = "500", description = "회원 가입 실패")
	})
	public ResponseEntity<Long> signup(@RequestBody RequestVipSignUpDTO requestVipSignUpDTO) {
		return ResponseEntity.ok().body(vipLoginService.signupProcess(requestVipSignUpDTO));
	}
}
