package com.dia.dia_be.controller.pb;

import static org.mockito.ArgumentMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.dia.dia_be.dto.pb.NotificationDTO;
import com.dia.dia_be.service.pb.intf.PbNotificationService;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(PbNotificationController.class)
class PbNotificationControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private PbNotificationService pbNotificationService;

	@Autowired
	private ObjectMapper objectMapper;

	private NotificationDTO notificationDTO1;
	private NotificationDTO notificationDTO2;
	private NotificationDTO notificationDTO3;

	@BeforeEach
	void setUp() {
		// 첫 번째
		notificationDTO1 = NotificationDTO.builder()
			.id(1L)
			.customerId(1L)
			.title("Test Title 1")
			.content("Test Content 1")
			.date(LocalDate.now())
			.isRead(false)
			.build();

		// 두 번째
		notificationDTO2 = NotificationDTO.builder()
			.id(2L)
			.customerId(2L)
			.title("Test Title 2")
			.content("Test Content 2")
			.date(LocalDate.now())
			.isRead(false)
			.build();

		// 세 번째
		notificationDTO3 = NotificationDTO.builder()
			.id(3L)
			.customerId(3L)
			.title("Test Title 3")
			.content("Test Content 3")
			.date(LocalDate.now())
			.isRead(true)
			.build();
	}

	// GET - 전체 쪽지 조회 테스트
	@Test
	void testGetAllNotifications() throws Exception {
		List<NotificationDTO> notifications = Arrays.asList(notificationDTO1, notificationDTO2, notificationDTO3);

		Mockito.when(pbNotificationService.getAllNotifications()).thenReturn(notifications);

		mockMvc.perform(get("/pb/notifications"))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.size()").value(3))
			.andExpect(jsonPath("$[0].id").value(1L))
			.andExpect(jsonPath("$[1].id").value(2L))
			.andExpect(jsonPath("$[2].id").value(3L));
	}

	// GET - 특정 고객에게 보낸 쪽지 조회 테스트
	@Test
	void testGetNotificationsByCustomerIds() throws Exception {
		List<NotificationDTO> notifications = Arrays.asList(notificationDTO1, notificationDTO2);

		Mockito.when(pbNotificationService.getNotificationsByCustomerIds(anyList())).thenReturn(notifications);

		mockMvc.perform(get("/pb/notifications/search")
				.param("id", "1", "2"))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.size()").value(2))
			.andExpect(jsonPath("$[0].customerId").value(1L))
			.andExpect(jsonPath("$[1].customerId").value(2L));
	}

	// GET - 쪽지 상세 조회 테스트
	@Test
	void testGetNotificationById() throws Exception {
		Mockito.when(pbNotificationService.getNotificationById(2L)).thenReturn(notificationDTO2);

		mockMvc.perform(get("/pb/notifications/2"))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.id").value(2L))
			.andExpect(jsonPath("$.title").value("Test Title 2"));
	}

	// POST - 여러 고객에게 쪽지 전송 테스트
	@Test
	void testSendNotifications() throws Exception {
		List<NotificationDTO> sentNotifications = Arrays.asList(notificationDTO1, notificationDTO2, notificationDTO3);

		Mockito.when(pbNotificationService.sendNotifications(any(NotificationDTO.class))).thenReturn(sentNotifications);

		mockMvc.perform(post("/pb/notifications/send")
				.param("customerIds", "1", "2", "3")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(notificationDTO1)))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.size()").value(3))
			.andExpect(jsonPath("$[0].customerId").value(1L))
			.andExpect(jsonPath("$[1].customerId").value(2L))
			.andExpect(jsonPath("$[2].customerId").value(3L));
	}
}
