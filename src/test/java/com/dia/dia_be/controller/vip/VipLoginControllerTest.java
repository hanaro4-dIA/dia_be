package com.dia.dia_be.controller.vip;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.time.LocalDate;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.dia.dia_be.dto.vip.loginDTO.RequestVipSignUpDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
public class VipLoginControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@Test()
	@DisplayName("VIP 회원가입 테스트")
	void vipSignUpTest() throws Exception {
		String url = "/vip/login/signup";

		RequestVipSignUpDTO vipSignUpInfo = RequestVipSignUpDTO.builder()
			.tel("010-1111-2222")
			.address("강동구 암사동 거주")
			.name("김은서")
			.email("kimeunseo@naver.com")
			.password("123123")
			.build();

		String requestBody = objectMapper.writeValueAsString(vipSignUpInfo);
		mockMvc.perform(post(url).contentType(MediaType.APPLICATION_JSON).content(requestBody))
			.andExpect(status().isOk())
			.andDo(print());

	}
}
