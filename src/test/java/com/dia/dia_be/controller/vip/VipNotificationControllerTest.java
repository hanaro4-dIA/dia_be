package com.dia.dia_be.controller.vip;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
public class VipNotificationControllerTest {

	@Autowired
	private MockMvc mockMvc;

	// GET {{base_url}}/vip/notifications
	// 해당 customer(=VIP)의 모든 알림을 가져옴
	@Test
	void findByCustomerIdTest() throws Exception {
		final String url = "/vip/notifications";

		mockMvc.perform(get(url).contentType(MediaType.APPLICATION_JSON))
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
		Long customerId = 1L;
		final String url = "/vip/notifications";

		mockMvc.perform(delete(url).contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(content().string("전체 알림이 삭제되었습니다."))
			.andDo(print());
	}

	// GET {{base_url}}/vip/notifications/{customerId}/read
	// 해당 customerId의 전체 알림 읽음 처리
	@Test
	void markAllNotificationsAsReadTest() throws Exception {
		Long customerId = 1L;
		final String url = "/vip/notifications";

		mockMvc.perform(patch(url).contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(content().string("전체 알림이 읽음 처리되었습니다."))
			.andDo(print());
	}
}
