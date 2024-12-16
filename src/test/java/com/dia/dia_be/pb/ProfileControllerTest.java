package com.dia.dia_be.pb;

import static org.assertj.core.api.AssertionsForClassTypes.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.nio.charset.StandardCharsets;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.dia.dia_be.domain.Hashtag;
import com.dia.dia_be.domain.Pb;
import com.dia.dia_be.dto.pb.profileDTO.ResponseProfileDTO;
import com.dia.dia_be.repository.PbRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@AutoConfigureMockMvc
public class ProfileControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	private PbRepository pbRepository;

	@BeforeEach
	void setUp() {
		Pb pb = Pb.builder()
			.loginId("Hana241201000")
			.name("손흥민")
			.office("하나은행 서압구정 골드클럽")
			.imageUrl("https://image.chosun.com/sitedata/image/201805/26/2018052601896_0.jpg")
			.availability(true)
			.password("TestPB123*")
			.tel("02-2243-1111")
			.introduce("고객의 꿈과 자산을 함께 설계하는 손흥민 PB 입니다.")
			.career("• 하나은행 서압구정 골드클럽 PB (현재)\n"
				+ " VIP 고객 자산관리 및 맞춤형 금융 컨설팅 제공\n"
				+ " 포트폴리오 설계 및 투자 자문 서비스\n"
				+ " \n"
				+ " • 2019-2023: 하나은행 본점 자산관리팀\n"
				+ " 고액 자산가 대상 프라이빗 뱅킹 전략 수립\n"
				+ " 다양한 금융상품 기획 및 실행\n"
				+ " \n"
				+ " • 2015-2019: 하나은행 영업점 자산관리 전문가\n"
				+ " 개인 고객 및 중소기업 대상 금융 컨설팅\n"
				+ " 연속 최우수 영업점 수상 기여")
			.build();

		pb.getHashtag().addAll(List.of(
			Hashtag.create(pb, "자산관리"),
			Hashtag.create(pb, "금융컨설팅"),
			Hashtag.create(pb, "포트폴리오")
		));

		pbRepository.save(pb);
	}

	@Test
	void getProfile() throws Exception {

		MvcResult result = mockMvc.perform(get("/pb/profile/{pbId}", 1L))
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

		assertThat(actualResponse.getHashtagList().get(0).getName()).isEqualTo("자산관리");
		assertThat(actualResponse.getHashtagList().get(1).getName()).isEqualTo("금융컨설팅");
		assertThat(actualResponse.getHashtagList().get(2).getName()).isEqualTo("포트폴리오");

	}

}

