package com.dia.dia_be.controller.vip;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;

import com.dia.dia_be.domain.PbSessionConst;
import com.dia.dia_be.domain.VipSessionConst;
import com.dia.dia_be.dto.pb.loginDTO.LoginDTO;
import com.dia.dia_be.dto.vip.loginDTO.VipLoginDTO;
import com.dia.dia_be.global.session.SessionManager;

@SpringBootTest
@AutoConfigureMockMvc
public class VipCategoryControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private SessionManager sessionManager;
	private MockHttpSession createMockSessionWithLoginDTO(long vipId) {
		MockHttpSession session = new MockHttpSession();
		session.setAttribute(VipSessionConst.LOGIN_VIP, new VipLoginDTO(vipId));
		sessionManager.sessionCreated(session);
		return session;
	}

	@Test
	void findAll() throws Exception {
		long vipId = 1L;

		final String url = "/vip/categories";

		mockMvc.perform(get(url).session(createMockSessionWithLoginDTO(vipId)))  // 세션 추가.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON))
			.andDo(print());

	}
}
