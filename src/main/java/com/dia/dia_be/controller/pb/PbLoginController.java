package com.dia.dia_be.controller.pb;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dia.dia_be.domain.Pb;
import com.dia.dia_be.domain.PbSessionConst;
import com.dia.dia_be.dto.pb.loginDTO.LoginForm;
import com.dia.dia_be.dto.pb.loginDTO.LoginResponseDTO;
import com.dia.dia_be.service.pb.intf.PbProfileService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("pb/login")
public class PbLoginController {

	private final PbProfileService pbProfileService;

	public PbLoginController(PbProfileService pbProfileService) {
		this.pbProfileService = pbProfileService;
	}

	@PostMapping("")
	public ResponseEntity<LoginResponseDTO> booksState(@RequestBody @Valid final LoginForm loginForm, HttpServletRequest request) {
		Pb pb = pbProfileService.login(loginForm.getId(),loginForm.getPw());
		LoginResponseDTO loginResponseDTO = new LoginResponseDTO(pb.getLoginId());

		//로그인 성공 처리

		//세션이 있으면 있는 세션 반환, 없으면 신규 세션 생성
		HttpSession session = request.getSession(true);

		//세션에 로그인 회원 정보 저장
		session.setAttribute(PbSessionConst.LOGIN_PB, loginResponseDTO);

		//profile 생성했는지 확인 후 안했으면 profile 설정 페이지로 넘어가게

		return ResponseEntity.ok().body(loginResponseDTO);

	}
}
