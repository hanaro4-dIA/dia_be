package com.dia.dia_be.controller.pb;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;

import com.dia.dia_be.domain.PbSessionConst;
import com.dia.dia_be.dto.pb.loginDTO.LoginDTO;

@SpringBootTest
@AutoConfigureMockMvc
public class PbLogoutControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Test
	void logout_Success() throws Exception {
		// Arrange: 세션 생성 및 데이터 저장
		MockHttpSession session = new MockHttpSession();
		LoginDTO loginDTO = new LoginDTO(123L);
		session.setAttribute(PbSessionConst.LOGIN_PB, loginDTO);

		// Act & Assert
		mockMvc.perform(post("/pb/logout")
				.session(session))
			.andExpect(status().isOk())
			.andExpect(content().string("Logout successful"));
	}

	@Test
	void logout_Fail_NoSession() throws Exception {
		// Act & Assert: 세션 없이 요청
		mockMvc.perform(post("/pb/logout"))
			.andExpect(status().isBadRequest())
			.andExpect(content().string("No active session found"));
	}

	@Test
	void logout_Fail_NoLoginData() throws Exception {
		// Arrange: 세션은 있지만 데이터 없음
		MockHttpSession session = new MockHttpSession();

		// Act & Assert
		mockMvc.perform(post("/pb/logout")
				.session(session))
			.andExpect(status().isBadRequest())
			.andExpect(content().string("No user is logged in"));
	}
}

