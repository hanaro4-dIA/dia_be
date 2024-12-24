package com.dia.dia_be.controller.vip;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;

import com.dia.dia_be.domain.VipSessionConst;
import com.dia.dia_be.dto.vip.loginDTO.VipLoginDTO;
import com.dia.dia_be.global.session.SessionManager;

import jakarta.servlet.http.Cookie;

@SpringBootTest
@AutoConfigureMockMvc
public class VipNotificationControllerTest {

	@Autowired
	private MockMvc mockMvc;
	@Autowired
	private SessionManager sessionManager;

	// GET {{base_url}}/vip/notifications
	// 해당 customer(=VIP)의 모든 알림을 가져옴
	@Test
	void findByCustomerIdTest() throws Exception {
		// 세션 생성
		MockHttpSession session = new MockHttpSession();
		VipLoginDTO loginDTO = new VipLoginDTO(1L);
		session.setAttribute(VipSessionConst.LOGIN_VIP, loginDTO);
		MockHttpServletResponse response = new MockHttpServletResponse();

		sessionManager.createSession(session,response);
		// JSESSIONID 쿠키를 명시적으로 추가
		Cookie jsessionCookie = new Cookie("JSESSIONID", session.getId());
		jsessionCookie.setPath("/");  // 쿠키 경로 설정
		jsessionCookie.setMaxAge(60 * 60);  // 쿠키 만료 시간 설정 (1시간)
		final String url = "/vip/notifications";

		mockMvc.perform(get(url).contentType(MediaType.APPLICATION_JSON).cookie(jsessionCookie))
			.andExpect(status().isOk())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON))
			.andExpect(jsonPath("$[0].title").exists())
			.andExpect(jsonPath("$[0].text").exists())
			.andDo(print());
	}

	// GET {{base_url}}/vip/notifications/{customerId}
	// 해당 customerId의 전체 알림 삭제
	@Test
	void deleteAllNotificationsTest() throws Exception {
		// 세션 생성
		MockHttpSession session = new MockHttpSession();
		VipLoginDTO loginDTO = new VipLoginDTO(1L);
		session.setAttribute(VipSessionConst.LOGIN_VIP, loginDTO);
		MockHttpServletResponse response = new MockHttpServletResponse();

		sessionManager.createSession(session,response);
		// JSESSIONID 쿠키를 명시적으로 추가
		Cookie jsessionCookie = new Cookie("JSESSIONID", session.getId());
		jsessionCookie.setPath("/");  // 쿠키 경로 설정
		jsessionCookie.setMaxAge(60 * 60);  // 쿠키 만료 시간 설정 (1시간)

		final String url = "/vip/notifications";

		mockMvc.perform(delete(url).contentType(MediaType.APPLICATION_JSON).cookie(jsessionCookie))
			.andExpect(status().isOk())
			.andExpect(content().string("전체 알림이 삭제되었습니다."))
			.andDo(print());
	}

	// GET {{base_url}}/vip/notifications/{customerId}/read
	// 해당 customerId의 전체 알림 읽음 처리
	@Test
	void markAllNotificationsAsReadTest() throws Exception {
		// 세션 생성
		MockHttpSession session = new MockHttpSession();
		VipLoginDTO loginDTO = new VipLoginDTO(1L);
		session.setAttribute(VipSessionConst.LOGIN_VIP, loginDTO);
		MockHttpServletResponse response = new MockHttpServletResponse();

		sessionManager.createSession(session,response);
		// JSESSIONID 쿠키를 명시적으로 추가
		Cookie jsessionCookie = new Cookie("JSESSIONID", session.getId());
		jsessionCookie.setPath("/");  // 쿠키 경로 설정
		jsessionCookie.setMaxAge(60 * 60);  // 쿠키 만료 시간 설정 (1시간)

		final String url = "/vip/notifications";

		mockMvc.perform(patch(url).contentType(MediaType.APPLICATION_JSON).cookie(jsessionCookie))
			.andExpect(status().isOk())
			.andExpect(content().string("전체 알림이 읽음 처리되었습니다."))
			.andDo(print());
	}
}
