package com.dia.dia_be.controller.pb;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.dia.dia_be.repository.JournalRepository;

@SpringBootTest
@AutoConfigureMockMvc
public class PbJournalControllerTest {

	@Autowired
	MockMvc mockMvc;

	@Autowired
	JournalRepository journalRepository;

	@Test
	@DisplayName("journal controller test - 상담 일지 리스트 조회")
	void getJournals() throws Exception {
		String url = "/pb/journals";
		mockMvc.perform(get(url).contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.size()").isNotEmpty())
			.andDo(print());
	}

	@Test
	@DisplayName("journal controller test - 특정 상담 일지 조회")
	void getJournal() throws Exception {
		String url = "/pb/journals/" + 1;
		mockMvc.perform(get(url).contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andDo(print());
	}

	@Test
	@DisplayName("journal controller test - 요청 상담 내용 자세히보기 조회")
	void getConsultingContent() throws Exception {
		String url = "/pb/journals/reserves/" + 1 + "/content";

		// response data가 string인지 검증
		mockMvc.perform(get(url).contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(content().contentType(MediaType.TEXT_PLAIN_VALUE + ";charset=UTF-8"))
			.andDo(print());
	}
}
