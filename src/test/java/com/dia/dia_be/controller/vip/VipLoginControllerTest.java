package com.dia.dia_be.controller.vip;

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;

import com.dia.dia_be.domain.Customer;
import com.dia.dia_be.domain.PbSessionConst;
import com.dia.dia_be.domain.VipSessionConst;
import com.dia.dia_be.dto.pb.loginDTO.LoginDTO;
import com.dia.dia_be.dto.vip.loginDTO.RequestVipSignUpDTO;
import com.dia.dia_be.dto.vip.loginDTO.VipLoginDTO;
import com.dia.dia_be.dto.vip.loginDTO.VipLoginForm;
import com.dia.dia_be.global.session.SessionManager;
import com.dia.dia_be.repository.CustomerRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.http.Cookie;

@SpringBootTest
@AutoConfigureMockMvc
public class VipLoginControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	private CustomerRepository customerRepository;

	@Autowired
	private SessionManager sessionManager;

	@Test()
	@DisplayName("VIP 회원가입 테스트")
	void vipSignUpTest() throws Exception {
		String url = "/vip/signup";

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

	@Test
	void checkLogin() throws Exception {
		Customer customer = customerRepository.findAll().get(0);
		VipLoginForm vipLoginForm = new VipLoginForm(customer.getEmail(),customer.getPassword());
		String url = "/vip/login";
		String requestBody = objectMapper.writeValueAsString(vipLoginForm);
		mockMvc.perform(post(url).contentType(MediaType.APPLICATION_JSON).content(requestBody))
			.andExpect(status().isOk())
			.andExpect(request().sessionAttribute("loginVIP", Matchers.notNullValue()))
			.andDo(print());
	}

	@Test
	void logout() throws Exception {
		// 세션 생성
		MockHttpSession session = new MockHttpSession();
		VipLoginDTO loginDTO = new VipLoginDTO(123L);
		session.setAttribute(VipSessionConst.LOGIN_VIP, loginDTO);
		sessionManager.sessionCreated(session);  // 세션이 실제로 생성되도록 설정

		// JSESSIONID 쿠키를 명시적으로 추가
		Cookie jsessionCookie = new Cookie("JSESSIONID", session.getId());
		jsessionCookie.setPath("/");  // 쿠키 경로 설정
		jsessionCookie.setMaxAge(60 * 60);  // 쿠키 만료 시간 설정 (1시간)

		// 로그아웃 요청 수행
		mockMvc.perform(post("/vip/logout")
				.cookie(jsessionCookie)  // 쿠키 추가
			)
			.andDo(print())  // 응답 출력
			.andExpect(status().isOk());  // 상태 코드 200 OK 확인
	}



}
