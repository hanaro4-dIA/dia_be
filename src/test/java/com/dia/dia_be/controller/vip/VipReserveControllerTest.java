package com.dia.dia_be.controller.vip;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.dia.dia_be.dto.vip.reserveDTO.RequestReserveDTO;
import com.dia.dia_be.dto.vip.reserveDTO.ResponseReserveDTO;
import com.dia.dia_be.service.vip.intf.VipReserveService;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.transaction.Transactional;

@SpringBootTest
@AutoConfigureMockMvc
// @TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class VipReserveControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	private VipReserveService vipReserveService;

	@Test
	void addReserveTest() throws Exception {
		final String url = "/vip/reserves";

		RequestReserveDTO sampleDTO = new RequestReserveDTO(LocalDate.now().plusDays(1), LocalTime.of(14, 0),
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

	@Test
	@Transactional
	void getReservesTest() throws Exception {
		final String url = "/vip/reserves";

		List<ResponseReserveDTO> reserves = vipReserveService.getReserves(1L);

		mockMvc.perform(get(url).contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON))
			.andExpect(jsonPath("$", hasSize(reserves.size())))
			.andExpect(jsonPath("$[0].id", is(reserves.get(0).getId().intValue())))
			.andExpect(jsonPath("$[0].title", is(reserves.get(0).getTitle())))
			.andExpect(jsonPath("$[0].time", is(reserves.get(0).getTime().toString())))
			.andDo(print());
	}
}
