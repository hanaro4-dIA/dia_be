package com.dia.dia_be.controller.pb;

import static org.assertj.core.api.AssertionsForClassTypes.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.nio.charset.StandardCharsets;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.transaction.annotation.Transactional;

import com.dia.dia_be.domain.Pb;
import com.dia.dia_be.domain.PbSessionConst;
import com.dia.dia_be.dto.pb.availabilityDTO.RequestAvailabilityDTO;
import com.dia.dia_be.dto.pb.loginDTO.LoginDTO;
import com.dia.dia_be.dto.pb.profileDTO.ResponseProfileDTO;
import com.dia.dia_be.repository.PbRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class PbProfileControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	private PbRepository pbRepository;

	MockHttpSession session;

	@BeforeEach
	void setupSession() {
		// 세션에 로그인 정보를 추가
		session = new MockHttpSession();
		LoginDTO loginDTO = new LoginDTO(1L);
		session.setAttribute(PbSessionConst.LOGIN_PB, loginDTO);
	}

	@Test
	void getProfile() throws Exception {

		MvcResult result = mockMvc.perform(get("/pb/profile").session(session)) // 세션 추가
			.andExpect(status().isOk())
			.andReturn();

		// 응답을 문자열로 변환

		String responseBody = result.getResponse().getContentAsString(StandardCharsets.UTF_8);
		// 응답 본문 출력
		System.out.println("Response Body: " + responseBody);

		// 문자열을 ResponseProfileDTO 객체로 변환
		ResponseProfileDTO actualResponse = objectMapper.readValue(responseBody, ResponseProfileDTO.class);

		// assertThat을 사용한 검증
		assertThat(actualResponse.getPbId()).isEqualTo(1L);
		assertThat(actualResponse.getName()).isEqualTo("손흥민");
		assertThat(actualResponse.getOffice()).isEqualTo("하나은행 서압구정 골드클럽");
		assertThat(actualResponse.isAvailability()).isTrue();
		assertThat(actualResponse.getIntroduce()).isEqualTo("고객의 꿈과 자산을 함께 설계하는 손흥민 PB 입니다.");
		assertThat(actualResponse.getImageUrl().isEmpty()).isFalse();
		assertThat(actualResponse.getHashtagList().get(0)).isEqualTo("자산관리");
		assertThat(actualResponse.getHashtagList().get(1)).isEqualTo("금융컨설팅");
		assertThat(actualResponse.getHashtagList().get(2)).isEqualTo("포트폴리오");

	}

	@Test
	public void testUpdateProfile() throws Exception {
		// API URL
		String url = "/pb/profile";

		// Introduce parameter
		String introduce = "This is my introduction.";

		// Mock Multipart File
		MockMultipartFile mockFile = new MockMultipartFile(
			"file",                       // RequestParam name
			"profile-image.jpg",          // Original file name
			"image/jpeg",                 // MIME type
			"<binary data>".getBytes()    // File content
		);

		// HashTagList 생성
		List<String> hashtags = List.of("tag1", "tag2", "tag3");

		// HashTagList를 JSON 형식으로 변환
		String hashTagListJson = objectMapper.writeValueAsString(hashtags);

		// MockMvc PUT 멀티파트 요청
		mockMvc.perform(multipart(url)
				.file(mockFile)                          // File 첨부
				.param("introduce", introduce)           // Introduce 필드
				.param("hashtags", hashTagListJson)   // HashTagList 필드
				.contentType(MediaType.MULTIPART_FORM_DATA) // Content-Type 설정
				.with(request -> {                       // PUT 메서드로 전환
					request.setMethod("PUT");
					return request;
				}).session(session)) // 세션 추가
			.andExpect(status().isOk())              // HTTP 200 OK 기대
			.andExpect(jsonPath("$.introduce").value("This is my introduction."))
			.andDo(print());                        // 요청/응답 로그 출력
	}

	@Test
	public void testUpdateProfileWithNoPng() throws Exception {
		// API URL
		String url = "/pb/profile";

		// Introduce parameter
		String introduce = "This is my introduction.";

		// HashTagList 생성
		List<String> hashtags = List.of("tag1", "tag2", "tag3");

		// HashTagList를 JSON 형식으로 변환
		String hashTagListJson = objectMapper.writeValueAsString(hashtags);
		System.out.println(hashTagListJson);
		// MockMvc PUT 멀티파트 요청
		mockMvc.perform(multipart(url)
				.param("introduce", introduce)           // Introduce 필드
				.param("hashtags", hashTagListJson)   // HashTagList 필드
				.contentType(MediaType.MULTIPART_FORM_DATA) // Content-Type 설정
				.with(request -> {                       // PUT 메서드로 전환
					request.setMethod("PUT");
					return request;
				}).session(session)) // 세션 추가
			.andExpect(status().isOk())              // HTTP 200 OK 기대
			.andExpect(jsonPath("$.introduce").value("This is my introduction."))
			.andDo(print());                         // 요청/응답 로그 출력
	}

	@Test
	void testUpdateAvailability() throws Exception {
		// 1. PB 엔티티 생성 및 저장
		Pb pb = Pb.create(
			"password123",                        // password
			"홍길동",                               // name
			"http://example.com/image.jpg",       // imageUrl
			"자산 관리 전문가입니다.",                  // introduce
			"하나은행 강남지점",                       // office
			"10년 경력",                            // career
			"honggildong",                         // loginId
			"010-1234-5678",                       // tel
			false                                  // availability (초기 상태)
		);

		// PB 엔티티를 저장
		pb = pbRepository.save(pb);

		// 2. 테스트에 사용할 데이터
		Long pbId = pb.getId(); // 저장된 PB의 ID
		boolean availability = true;

		// RequestAvailabilityDTO 객체 생성
		RequestAvailabilityDTO requestDTO = RequestAvailabilityDTO.builder()
			.pbId(pbId)
			.availability(availability)
			.build();

		// JSON 형식으로 변환
		String requestJson = objectMapper.writeValueAsString(requestDTO);

		// 3. MockMvc를 사용한 PUT 요청 실행
		mockMvc.perform(put("/pb/availability")
				.contentType(MediaType.APPLICATION_JSON)
				.content(requestJson).session(session)) // 세션 추가
			.andExpect(status().isOk()) // HTTP 200 OK 기대
			.andExpect(jsonPath("$.pbId").value(pbId))
			.andExpect(jsonPath("$.availability").value(availability))
			.andDo(print()); // 요청/응답 로그 출력

		// 4. DB에서 PB 엔티티 다시 조회하여 변경된 상태 확인
		Pb updatedPb = pbRepository.findById(pbId).orElseThrow();
		assertThat(updatedPb.isAvailability()).isTrue();
	}

}
