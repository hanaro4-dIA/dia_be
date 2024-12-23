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
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;

import com.dia.dia_be.domain.VipSessionConst;
import com.dia.dia_be.dto.vip.loginDTO.VipLoginDTO;
import com.dia.dia_be.dto.vip.reserveDTO.RequestReserveDTO;
import com.dia.dia_be.dto.vip.reserveDTO.ResponseReserveDTO;
import com.dia.dia_be.global.session.SessionManager;
import com.dia.dia_be.service.vip.intf.VipReserveService;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.http.Cookie;
import jakarta.transaction.Transactional;

@SpringBootTest
@AutoConfigureMockMvc
// @TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class VipReserveControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private SessionManager sessionManager;

	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	private VipReserveService vipReserveService;

	@Test
	void addReserveTest() throws Exception {
		// 세션 생성
		MockHttpSession session = new MockHttpSession();
		VipLoginDTO loginDTO = new VipLoginDTO(1L);
		session.setAttribute(VipSessionConst.LOGIN_VIP, loginDTO);
		sessionManager.sessionCreated(session);  // 세션이 실제로 생성되도록 설정

		// JSESSIONID 쿠키를 명시적으로 추가
		Cookie jsessionCookie = new Cookie("JSESSIONID", session.getId());
		jsessionCookie.setPath("/");  // 쿠키 경로 설정
		jsessionCookie.setMaxAge(60 * 60);  // 쿠키 만료 시간 설정 (1시간)

		final String url = "/vip/reserves";

		RequestReserveDTO sampleDTO = new RequestReserveDTO(LocalDate.now().plusDays(1), LocalTime.of(14, 0),
			2L,
			"상담제목1", "상담내용");
		mockMvc.perform(
				post(url).content(objectMapper.writeValueAsString(sampleDTO))
					.contentType(MediaType.APPLICATION_JSON)
					.cookie(jsessionCookie)
			).andExpect(status().isOk())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON))
			.andExpect(content().string(not("")))
			.andDo(print());
	}

	@Test
	void getReserveInfoTest() throws Exception {
		// 세션 생성
		MockHttpSession session = new MockHttpSession();
		VipLoginDTO loginDTO = new VipLoginDTO(1L);
		session.setAttribute(VipSessionConst.LOGIN_VIP, loginDTO);
		sessionManager.sessionCreated(session);  // 세션이 실제로 생성되도록 설정

		// JSESSIONID 쿠키를 명시적으로 추가
		Cookie jsessionCookie = new Cookie("JSESSIONID", session.getId());
		jsessionCookie.setPath("/");  // 쿠키 경로 설정
		jsessionCookie.setMaxAge(60 * 60);  // 쿠키 만료 시간 설정 (1시간)

		final String url = "/vip/reserves/info";

		mockMvc.perform(get(url)
				.contentType(MediaType.APPLICATION_JSON).cookie(jsessionCookie))
			.andExpect(status().isOk())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON))
			.andExpect(jsonPath("$.pbName", notNullValue()))
			.andExpect(jsonPath("$.vipName", notNullValue()))
			.andDo(print());
	}

	@Test
	@Transactional
	void getReservesTest() throws Exception {
		// 세션 생성
		MockHttpSession session = new MockHttpSession();
		VipLoginDTO loginDTO = new VipLoginDTO(1L);
		session.setAttribute(VipSessionConst.LOGIN_VIP, loginDTO);
		sessionManager.sessionCreated(session);  // 세션이 실제로 생성되도록 설정

		// JSESSIONID 쿠키를 명시적으로 추가
		Cookie jsessionCookie = new Cookie("JSESSIONID", session.getId());
		jsessionCookie.setPath("/");  // 쿠키 경로 설정
		jsessionCookie.setMaxAge(60 * 60);  // 쿠키 만료 시간 설정 (1시간)

		final String url = "/vip/reserves";

		List<ResponseReserveDTO> reserves = vipReserveService.getReserves(1L);

		mockMvc.perform(get(url).contentType(MediaType.APPLICATION_JSON).cookie(jsessionCookie))
			.andExpect(status().isOk())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON))
			.andExpect(jsonPath("$", hasSize(reserves.size())))
			.andExpect(jsonPath("$[0].id", is(reserves.get(0).getId().intValue())))
			.andExpect(jsonPath("$[0].title", is(reserves.get(0).getTitle())))
			.andExpect(jsonPath("$[0].date", is(reserves.get(0).getDate().toString())))
			.andExpect(jsonPath("$[0].time", is(reserves.get(0).getTime().toString())))
			.andDo(print());
	}

	@Test
	@Transactional
	void getReserveByIdTest() throws Exception {
		// 세션 생성
		MockHttpSession session = new MockHttpSession();
		VipLoginDTO loginDTO = new VipLoginDTO(1L);
		session.setAttribute(VipSessionConst.LOGIN_VIP, loginDTO);
		sessionManager.sessionCreated(session);  // 세션이 실제로 생성되도록 설정

		// JSESSIONID 쿠키를 명시적으로 추가
		Cookie jsessionCookie = new Cookie("JSESSIONID", session.getId());
		jsessionCookie.setPath("/");  // 쿠키 경로 설정
		jsessionCookie.setMaxAge(60 * 60);  // 쿠키 만료 시간 설정 (1시간)

		final Long customerId = 1L;
		List<ResponseReserveDTO> reserves = vipReserveService.getReserves(customerId);
		reserves = reserves.stream().filter(reserve -> reserve.getDate().isAfter(LocalDate.now())).toList();
		Long reserveId = reserves.get(0).getId();

		final String url = "/vip/reserves/" + reserveId;
		mockMvc.perform(get(url).contentType(MediaType.APPLICATION_JSON).cookie(jsessionCookie))
			.andExpect(status().isOk())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON))
			.andExpect(jsonPath("$.pbName", notNullValue()))
			.andExpect(jsonPath("$.customerName", notNullValue()))
			.andExpect(jsonPath("$.title", is(reserves.get(0).getTitle())))
			.andExpect(jsonPath("$.date", is(reserves.get(0).getDate().toString())))
			.andExpect(jsonPath("$.time", is(reserves.get(0).getTime().toString())))
			.andDo(print());
	}

	@Test
	@Transactional
	void deleteReserveByIdTest() throws Exception {
		// 세션 생성
		MockHttpSession session = new MockHttpSession();
		VipLoginDTO loginDTO = new VipLoginDTO(1L);
		session.setAttribute(VipSessionConst.LOGIN_VIP, loginDTO);
		sessionManager.sessionCreated(session);  // 세션이 실제로 생성되도록 설정

		// JSESSIONID 쿠키를 명시적으로 추가
		Cookie jsessionCookie = new Cookie("JSESSIONID", session.getId());
		jsessionCookie.setPath("/");  // 쿠키 경로 설정
		jsessionCookie.setMaxAge(60 * 60);  // 쿠키 만료 시간 설정 (1시간)

		List<ResponseReserveDTO> reserves = vipReserveService.getReserves(loginDTO.getCustomerId());
		reserves = reserves.stream().filter(reserve -> reserve.getDate().isAfter(LocalDate.now())).toList();
		Long reserveId = reserves.get(0).getId();

		final String url = "/vip/reserves/" + reserveId;
		mockMvc.perform(delete(url).contentType(MediaType.APPLICATION_JSON).cookie(jsessionCookie))
			.andExpect(status().isOk())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON))
			.andDo(print());
	}
}
