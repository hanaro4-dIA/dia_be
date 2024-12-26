package com.dia.dia_be.controller.pb;

import static org.assertj.core.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.nio.charset.StandardCharsets;
import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.dia.dia_be.domain.Pb;
import com.dia.dia_be.domain.PbSessionConst;
import com.dia.dia_be.dto.pb.customerDTO.ResponseCustomerDTO;
import com.dia.dia_be.domain.Customer;
import com.dia.dia_be.dto.pb.loginDTO.LoginDTO;
import com.dia.dia_be.repository.CustomerRepository;
import com.dia.dia_be.repository.PbRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.transaction.Transactional;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@Transactional
@AutoConfigureMockMvc
public class PbCustomerControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private CustomerRepository customerRepository;

	@Autowired
	private PbRepository pbRepository;

	private final String baseUrl = "http://localhost:8080/pb/customers"; // 기본 URL

	private MockHttpSession createMockSessionWithLoginDTO(long pbId) {
		MockHttpSession session = new MockHttpSession();
		session.setAttribute(PbSessionConst.LOGIN_PB, new LoginDTO(pbId));
		return session;
	}


	@BeforeEach
	void setUp() {
		Pb pb1 = Pb.create(
			"password1",
			"김경민",
			"https://image.url",
			"introduce text",
			"서울특별시 강남구",
			"10년 경력",
			"login1",
			"010-1111-2222",
			true
		);

		Pb pb2 = Pb.create(
			"password2",
			"이상호",
			"https://anotherimage.url",
			"introduce text",
			"서울특별시 마포구",
			"5년 경력",
			"login2",
			"010-3333-4444",
			true
		);

		pbRepository.save(pb1);
		pbRepository.save(pb2);

		Customer customer1 = Customer.builder()
			.name("강재준")
			.password("password1")
			.email("email1@example.com")
			.address("서울특별시 강남구")
			.tel("010-9945-5020")
			.count(10)
			.memo("강남구 거주, 안정적 자산 관리 필요.")
			.date(LocalDate.of(2023, 10, 1))
			.pb(pb1)
			.build();
		customerRepository.save(customer1);

		Customer customer2 = Customer.builder()
			.name("김철수")
			.password("password2")
			.email("email2@example.com")
			.address("서울특별시 마포구")
			.tel("010-1234-5678")
			.count(5)
			.memo("마포구 거주, 재테크 관심 있음.")
			.date(LocalDate.of(2023, 11, 1))
			.pb(pb2)
			.build();
		customerRepository.save(customer2);

		ResponseCustomerDTO customerDto1 = ResponseCustomerDTO.toDto(customer1);
		ResponseCustomerDTO customerDto2 = ResponseCustomerDTO.toDto(customer2);

		System.out.println("Customer 1 DTO: " + customerDto1);
		System.out.println("Customer 2 DTO: " + customerDto2);
	}


	@AfterEach
	void tearDown() {
		customerRepository.deleteAll();
	}

	// GET {{base_url}}/pb/customers/list?pbId={{pbId}}
	@Test
	void testGetCustomerListByPbId() throws Exception {
		long pbId = 1L;

		MvcResult result = mockMvc.perform(get(baseUrl + "/list")
				.session(createMockSessionWithLoginDTO(pbId)))  // 세션 추가
			.andExpect(status().isOk())
			.andReturn();

		String responseBody = result.getResponse().getContentAsString(StandardCharsets.UTF_8);

		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.findAndRegisterModules();

		ResponseCustomerDTO[] customerList = objectMapper.readValue(responseBody, ResponseCustomerDTO[].class);

		for (ResponseCustomerDTO customerDTO : customerList) {
			assertThat(customerDTO.getPbId()).isEqualTo(pbId);
		}
	}


	// GET {{base_url}}/pb/customers/search?name={{customerName}}
	@Test
	void testSearchCustomer() throws Exception {
		String name = "강재준";
		long pbId = 1L;

		MvcResult result = mockMvc.perform(get(baseUrl + "/search")
				.session(createMockSessionWithLoginDTO(pbId))  // 세션 추가
				.param("name", name))
			.andExpect(status().isOk())
			.andReturn();

		String responseBody = result.getResponse().getContentAsString(StandardCharsets.UTF_8);

		assertThat(responseBody).contains(name);
	}


	// GET {{base_url}}/pb/customers/list/{{customerId}}
	@Test
	void testGetCustomerDetail() throws Exception {
		long customerId = 1L;
		long pbId = 1L;

		MvcResult result = mockMvc.perform(get(baseUrl + "/list/" + customerId)
				.session(createMockSessionWithLoginDTO(pbId)))  // 세션 추가
			.andExpect(status().isOk())
			.andReturn();

		String responseBody = result.getResponse().getContentAsString(StandardCharsets.UTF_8);

		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.findAndRegisterModules();

		ResponseCustomerDTO customerDTO = objectMapper.readValue(responseBody, ResponseCustomerDTO.class);

		assertThat(customerDTO.getId()).isEqualTo(1L);
		assertThat(customerDTO.getPbId()).isEqualTo(1L);
		assertThat(customerDTO.getName()).isEqualTo("강재준");
		assertThat(customerDTO.getTel()).isEqualTo("010-9945-5020");
		assertThat(customerDTO.getAddress()).isEqualTo("서울특별시 강남구");
		assertThat(customerDTO.getDate()).isEqualTo(LocalDate.parse("2023-10-01"));
		assertThat(customerDTO.getMemo()).isEqualTo("강남구 거주, 안정적 자산 관리 필요.");
	}


	// POST {{base_url}}/pb/customers/{{customerId}}/memo
	@Test
	void testUpdateCustomerMemo() throws Exception {
		long customerId = 1L;
		long pbId = 1L;
		String newMemo = "새로운 메모 내용";

		String requestJson = String.format("{\"memo\": \"%s\"}", newMemo);

		MvcResult result = mockMvc.perform(post(baseUrl + "/" + customerId + "/memo")
				.session(createMockSessionWithLoginDTO(pbId))  // 세션 추가
				.contentType(MediaType.APPLICATION_JSON)
				.content(requestJson))
			.andExpect(status().isOk())
			.andReturn();

		String responseBody = result.getResponse().getContentAsString(StandardCharsets.UTF_8);
		System.out.println(responseBody);

		assertThat(responseBody).contains(newMemo);
	}

}
