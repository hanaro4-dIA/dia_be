package com.dia.dia_be.controller.pb;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.dia.dia_be.domain.Pb;
import com.dia.dia_be.dto.pb.loginDTO.LoginForm;
import com.dia.dia_be.repository.PbRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
public class PbLoginControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;
	@Autowired
	private PbRepository pbRepository;
	@Test
	void testLoginFormPost() throws Exception {
		Pb pb = pbRepository.findAll().get(0);
		LoginForm loginForm = new LoginForm(pb.getLoginId(), pb.getPassword());

		String loginFormJson = objectMapper.writeValueAsString(loginForm);

		mockMvc.perform(post("/pb/login")
				.contentType(MediaType.APPLICATION_JSON)
				.content(loginFormJson))
			.andExpect(status().isOk())
			.andExpect(request().sessionAttribute("loginPB", org.hamcrest.Matchers.notNullValue()))
			.andExpect(jsonPath("$.message").value("Login successful"));

	}

	@Test
	void testFailLoginFormPost() throws Exception {
		LoginForm loginForm = new LoginForm("testUser", "testPassword");

		String loginFormJson = objectMapper.writeValueAsString(loginForm);

		mockMvc.perform(post("/pb/login")
				.contentType(MediaType.APPLICATION_JSON)
				.content(loginFormJson))
			.andExpect(status().is5xxServerError());
	}
}
