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
public class JournalControllerTest {

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
}
