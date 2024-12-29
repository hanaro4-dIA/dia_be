package com.dia.dia_be.controller.pb;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dia.dia_be.domain.Pb;
import com.dia.dia_be.domain.PbSessionConst;
import com.dia.dia_be.dto.pb.loginDTO.LoginDTO;
import com.dia.dia_be.dto.pb.loginDTO.LoginForm;
import com.dia.dia_be.dto.pb.loginDTO.LoginResponseDTO;
import com.dia.dia_be.service.pb.intf.PbProfileService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("pb/login")
@Tag(name = "사용자 인증 및 계정 관리", description = "회원가입, 로그인, 로그아웃 관련 API 모음")
public class PbLoginController {

	private final PbProfileService pbProfileService;

	public PbLoginController(PbProfileService pbProfileService) {
		this.pbProfileService = pbProfileService;
	}

	@PostMapping("")
	@Operation(summary = "pb 로그인", description = "PB 로그인")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "로그인 성공", content = @Content(mediaType = "application/json")),
		@ApiResponse(responseCode = "500", description = "로그인 실패")
	})
	public ResponseEntity<LoginResponseDTO> pbLogin(@RequestBody @Valid final LoginForm loginForm,
		HttpServletRequest request, HttpServletResponse response) {
		Pb pb = pbProfileService.login(loginForm.getId(), loginForm.getPw());
		LoginDTO loginDTO = new LoginDTO(pb.getId());

		HttpSession session = request.getSession(true);
		session.setAttribute(PbSessionConst.LOGIN_PB, loginDTO);
		session.setAttribute("SameSite", "None");  // 크로스 도메인 요청 허용

		Cookie cookie = new Cookie("JSESSIONID", session.getId());
		cookie.setPath("/");
		cookie.setHttpOnly(true);
		cookie.setSecure(true);
		cookie.setMaxAge(7 * 24 * 60 * 60);  // 7일 유지
		cookie.setAttribute("SameSite", "None");  // 크로스 도메인 요청 허용
		response.addCookie(cookie);

		return ResponseEntity.ok().body(new LoginResponseDTO("Login successful"));
	}
}
