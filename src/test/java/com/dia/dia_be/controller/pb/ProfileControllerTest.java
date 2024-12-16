package com.dia.dia_be.controller.pb;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@SpringBootTest
@AutoConfigureMockMvc
class ProfileControllerTest {

	@Autowired
	private MockMvc mvc;

	@Test
	void setUp() throws Exception {

	}

	@Test
	void getProfile() throws Exception {
		mvc.perform(
				MockMvcRequestBuilders.get("/pb/profile/{id}"))
			.andExpect(status().isOk());
	}
}