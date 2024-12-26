package com.dia.dia_be.dto.vip.loginDTO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class VipLoginForm {
	@Schema(description = "이메일", example = "email1@example.com")
	String id;
	@Schema(description = "비밀번호", example = "password1")
	String pw;
}
