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
	@Operation(summary = "Customer 리스트 조회", description = "모든 Customer 리스트를 조회합니다.")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "로그인 성공", content = @Content(mediaType = "application/json")),
		@ApiResponse(responseCode = "500", description = "로그인 실패")
	})
	public ResponseEntity<LoginResponseDTO> booksState(@RequestBody @Valid final LoginForm loginForm,
		HttpServletRequest request) {
		log.info("가나다" + loginForm.getId() + loginForm.getPw());
		Pb pb = pbProfileService.login(loginForm.getId(), loginForm.getPw());
		LoginDTO loginDTO = new LoginDTO(pb.getLoginId());

		HttpSession session = request.getSession(true);
		session.setAttribute(PbSessionConst.LOGIN_PB, loginDTO);
		return ResponseEntity.ok().body(new LoginResponseDTO("Login successful"));
	}
}
