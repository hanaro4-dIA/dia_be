package com.dia.dia_be.dto.pb.loginDTO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class LoginForm {
	@Schema(description = "PB의 ID", example = "Hana241201000")
	String id;
	@Schema(description = "PB의 PW", example = "TestPB123*")
	String pw;
}
