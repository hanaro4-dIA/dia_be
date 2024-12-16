package com.dia.dia_be.controller;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.time.LocalDate;
import java.time.LocalTime;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.dia.dia_be.dto.vip.RequestReservePostDTO;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
// @TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ReserveControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@Test
	void addReserveTest() throws Exception {
		final String url = "/vip/reserves";

		RequestReservePostDTO sampleDTO = new RequestReservePostDTO(LocalDate.now().plusDays(1), LocalTime.of(14, 0),
			2L,
			"상담제목1", "상담내용");
		mockMvc.perform(
				post(url).content(objectMapper.writeValueAsString(sampleDTO)).contentType(MediaType.APPLICATION_JSON)
			).andExpect(status().isOk())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON))
			.andExpect(content().string(not("")))
			.andDo(print());
	}

	@Test
	void getReserveInfoTest() throws Exception {
		final String url = "/vip/reserves/info";

		mockMvc.perform(get(url)
				.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON))
			.andExpect(jsonPath("$.pbName", notNullValue()))
			.andExpect(jsonPath("$.vipName", notNullValue()))
			.andDo(print());
	}
}
