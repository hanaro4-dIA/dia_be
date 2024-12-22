package com.dia.dia_be.controller.pb;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dia.dia_be.domain.PbSessionConst;
import com.dia.dia_be.dto.pb.loginDTO.LoginDTO;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("pb/logout")
public class PbLogoutController {

	@PostMapping()
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
