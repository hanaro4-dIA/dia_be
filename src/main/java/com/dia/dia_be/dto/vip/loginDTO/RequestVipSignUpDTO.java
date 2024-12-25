package com.dia.dia_be.dto.vip.loginDTO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RequestVipSignUpDTO {

	@Schema(description = "이름", example = "홍길동")
	private String name;
	@Schema(description = "이메일", example = "email123@example.com")
	private String email;
	@Schema(description = "비밀번호", example = "password123")
	private String password;
	@Schema(description = "전화번호", example = "010-1111-1111")
	private String tel;
	@Schema(description = "주소", example = "서울특별시 낙원구 행복동")
	private String address;

}
