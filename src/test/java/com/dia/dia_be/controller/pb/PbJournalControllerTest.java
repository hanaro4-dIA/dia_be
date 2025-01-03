package com.dia.dia_be.controller.pb;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;

import com.dia.dia_be.domain.Journal;
import com.dia.dia_be.domain.PbSessionConst;
import com.dia.dia_be.dto.pb.journalDTO.RequestJournalDTO;
import com.dia.dia_be.dto.pb.loginDTO.LoginDTO;
import com.dia.dia_be.repository.JournalRepository;
import com.dia.dia_be.service.pb.intf.PbJournalService;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
public class PbJournalControllerTest {

	@Autowired
	MockMvc mockMvc;

	@Autowired
	ObjectMapper objectMapper;

	@Autowired
	PbJournalService pbJournalService;

	@Autowired
	JournalRepository journalRepository;

	MockHttpSession session;

	@BeforeEach
	void setUp() {
		// 로그인 정보를 세션에 담기
		LoginDTO loginDTO = new LoginDTO(1L);

		session = new MockHttpSession();
		session.setAttribute(PbSessionConst.LOGIN_PB, loginDTO);
	}

	@Test
	@DisplayName("journal controller test - 상담 일지 리스트 조회")
	void getJournals() throws Exception {
		String url = "/pb/journals";
		mockMvc.perform(get(url)
				.session(session) // 세션 추가
				.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.size()").isNotEmpty())
			.andDo(print());
	}

	@Test
	@DisplayName("journal controller test - 특정 상담 일지 조회")
	void getJournal() throws Exception {
		String url = "/pb/journals/1";
		mockMvc.perform(get(url)
				.session(session) // 세션 추가
				.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andDo(print());
	}

	@Test
	@DisplayName("journal controller test - 요청 상담 내용 자세히보기 조회")
	void getConsultingContent() throws Exception {
		String url = "/pb/journals/reserves/1/content";

		mockMvc.perform(get(url)
				.session(session) // 세션 추가
				.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(content().contentType(MediaType.TEXT_PLAIN_VALUE + ";charset=UTF-8"))
			.andDo(print());
	}

	@Test
	@DisplayName("journal controller test - 상담 일지 저장하기")
	void saveJournalTest() throws Exception {
		String url = "/pb/journals";
		List<Long> keys = new ArrayList<>();
		keys.add(2L);

		RequestJournalDTO journalDTO = RequestJournalDTO.builder()
			.consultingId(1L)
			.categoryId(2L)
			.consultingTitle("상담 일지 저장 테스트 타이틀")
			.journalContents("상담 일지 저장 테스트 PB의 기록")
			.recommendedProductsKeys(keys)
			.build();

		String requestBody = objectMapper.writeValueAsString(journalDTO);

		mockMvc.perform(post(url)
				.session(session) // 세션 추가
				.contentType(MediaType.APPLICATION_JSON)
				.content(requestBody))
			.andExpect(status().isOk())
			.andDo(print());
	}

	@Test
	@DisplayName("journal controller test - 태그 기반 상품 검색")
	void getProductsTest() throws Exception {
		String tag = "대출";
		String url = "/pb/journals/products?tag=" + tag;
		mockMvc.perform(get(url)
				.session(session) // 세션 추가
				.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.size()").isNotEmpty())
			.andDo(print());
	}

	@Test
	@DisplayName("getScripts")
	void getScripts() throws Exception {
		Journal journal = journalRepository.findAll().get(0);
		String url = "/pb/journals/" + journal.getId() + "/scripts";
		mockMvc.perform(get(url)
				.session(session) // 세션 추가
				.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.size()").isNotEmpty())
			.andDo(print());
	}

	@Test
	@DisplayName("임시 저장된 상담 일지 불러오기 테스트")
	void getTemporarySavedJournalTest() throws Exception {
		long id = 10L;
		boolean complete = false;
		String url = "/pb/journals/" + id + "/status?complete=" + complete;

		mockMvc.perform(get(url)
				.session(session) // 세션 추가
				.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andDo(print());
	}
}
