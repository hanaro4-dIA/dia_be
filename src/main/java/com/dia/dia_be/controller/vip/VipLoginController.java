package com.dia.dia_be.controller.vip;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dia.dia_be.dto.vip.loginDTO.RequestVipSignUpDTO;
import com.dia.dia_be.dto.vip.profileDTO.LoginResponseDTO;
import com.dia.dia_be.service.vip.intf.VipLoginService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;


import com.dia.dia_be.dto.vip.profileDTO.LoginForm;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("vip/login")
public class VipLoginController {
	private final VipLoginService vipLoginService;

	public VipLoginController(VipLoginService vipLoginService) {
		this.vipLoginService = vipLoginService;
	}

	@PostMapping()
	@Operation(summary = "vip 회원 확인", description = "vip login jwt 발급이 가능한지 판단합니다.")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "회원 존재", content = @Content(mediaType = "application/json")),
		@ApiResponse(responseCode = "500", description = "회원 비존재")
	})
	public ResponseEntity<LoginResponseDTO> checkLogin(@RequestBody LoginForm loginForm){
		vipLoginService.checkLogin(loginForm);
		return ResponseEntity.ok().body(new LoginResponseDTO("this user exists"));
	}

	@PostMapping("/signup")
	@Tag(name = "VIP 회원 가입")
	@Operation(summary = "VIP 회원 가입", description = "VIP 회원 가입 API")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "회원 가입 성공", content = @Content(mediaType = "application/json")),
		@ApiResponse(responseCode = "500", description = "회원 가입 실패")
	})
	public ResponseEntity<?> signup(@RequestBody RequestVipSignUpDTO requestVipSignUpDTO){
		return ResponseEntity.ok().body(vipLoginService.signupProcess(requestVipSignUpDTO));
	}
}
