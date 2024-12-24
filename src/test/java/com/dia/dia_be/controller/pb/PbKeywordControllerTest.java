package com.dia.dia_be.controller.pb;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.dia.dia_be.domain.PbSessionConst;
import com.dia.dia_be.dto.pb.loginDTO.LoginDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
public class PbKeywordControllerTest {

	@Autowired
	MockMvc mockMvc;

	MockHttpSession session;

	@BeforeEach
	void setupSession() {
		// 세션에 로그인 정보를 추가
		session = new MockHttpSession();
		LoginDTO loginDTO = new LoginDTO(1L);
		session.setAttribute(PbSessionConst.LOGIN_PB, loginDTO);
	}

	@Test
	void getKeywordsTest() throws Exception {
		String url = "/pb/keywords";

		mockMvc.perform(get(url)
				.contentType(MediaType.APPLICATION_JSON)
				.session(session)) // 세션 추가
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.size()").isNotEmpty())
			.andDo(print());
	}

	@Test
	void getKeywordTest() throws Exception {
		long id = 1L;
		String url = "/pb/keywords/" + id;

		mockMvc.perform(get(url)
				.contentType(MediaType.APPLICATION_JSON)
				.session(session)) // 세션 추가
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.content").exists())
			.andExpect(jsonPath("$.title").exists())
			.andDo(print());
	}
}
