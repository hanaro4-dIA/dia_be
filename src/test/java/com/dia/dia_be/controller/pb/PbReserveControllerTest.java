package com.dia.dia_be.controller.pb;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.dia.dia_be.domain.Category;
import com.dia.dia_be.domain.Consulting;
import com.dia.dia_be.domain.Customer;
import com.dia.dia_be.domain.Pb;
import com.dia.dia_be.dto.pb.reservesDTO.ResponseReserveDTO;
import com.dia.dia_be.repository.CategoryRepository;
import com.dia.dia_be.repository.ConsultingRepository;
import com.dia.dia_be.repository.CustomerRepository;
import com.dia.dia_be.repository.PbRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.transaction.Transactional;

@SpringBootTest
@Transactional
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@AutoConfigureMockMvc
public class PbReserveControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ConsultingRepository consultingRepository;

	@Autowired
	private CategoryRepository categoryRepository;

	@Autowired
	private CustomerRepository customerRepository;

	@Autowired
	private PbRepository pbRepository;

	private final ObjectMapper objectMapper = new ObjectMapper().findAndRegisterModules();

	private final String baseUrl = "http://localhost:8080/pb/reserves";

	Consulting consulting1;
	Consulting consulting2;
	Pb pb;
	Consulting consulting3;

	@BeforeEach
	void setUp() {
		Category category = Category.create("Finance");
		categoryRepository.save(category);

		pb = pbRepository.findById(1L).orElseThrow(() -> new RuntimeException("PB not found"));

		Customer customer = Customer.create(
			pb,
			LocalDate.now(),
			null,
			0,
			"Test Memo",
			"test@example.com",
			"password",
			"Test Customer",
			"010-1234-5678",
			"Test Address"
		);
		customerRepository.save(customer);

		// approve = false
		consulting1 = Consulting.create(
			category,
			customer,
			"Consulting Title 1",
			LocalDate.of(2024, 01, 30),
			LocalTime.of(14, 0),
			LocalDate.now(),
			LocalTime.now(),
			"Consulting Content 1",
			false
		);
		consulting1 = consultingRepository.save(consulting1);

		// approve = true
		consulting2 = Consulting.create(
			category,
			customer,
			"Consulting Title 2",
			LocalDate.of(2024, 03, 07),
			LocalTime.of(15, 0),
			LocalDate.now(),
			LocalTime.now(),
			"Consulting Content 2",
			true
		);
		consulting2 = consultingRepository.save(consulting2);

		// approve = true && hope_date > CURRENT_DATE
		consulting3 = Consulting.create(
			category,
			customer,
			"Consulting Title 3",
			LocalDate.of(2024, 12, 24),
			LocalTime.of(18, 0),
			LocalDate.now(),
			LocalTime.now(),
			"Consulting Content 3",
			true
		);
		consulting3 = consultingRepository.save(consulting3);
	}

	// 들어온 상담 요청 조회 test (status=false)
	@Test
	void testGetPbNotApprovedReservesList() throws Exception {
		MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get(baseUrl + "?status=false"))
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andReturn();

		String responseBody = result.getResponse().getContentAsString();

		ResponseReserveDTO[] getApprovedReserves = objectMapper.readValue(responseBody,
			ResponseReserveDTO[].class);

		Assertions.assertThat(getApprovedReserves).isNotNull();

		Long[] ids = Arrays.stream(getApprovedReserves)
			.map(ResponseReserveDTO::getId)
			.toArray(Long[]::new);

		Assertions.assertThat(consulting1.getId()).isIn((Object[])ids);
		Assertions.assertThat(consulting2.getId()).isNotIn((Object[])ids);
		Assertions.assertThat(consulting3.getId()).isNotIn((Object[])ids);
	}

	// 예정된 상담 일정 조회 test (status=true&type=upcoming)
	@Test
	void testGetPbApprovedAndUpcomingReservesList() throws Exception {
		MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get(baseUrl + "?status=true&type=upcoming"))
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andReturn();

		String responseBody = result.getResponse().getContentAsString();

		ResponseReserveDTO[] getUpcomingReserves = objectMapper.readValue(responseBody,
			ResponseReserveDTO[].class);

		Assertions.assertThat(getUpcomingReserves).isNotNull();

		Long[] ids = Arrays.stream(getUpcomingReserves)
			.map(ResponseReserveDTO::getId)
			.toArray(Long[]::new);

		Assertions.assertThat(consulting1.getId()).isNotIn((Object[])ids);
		Assertions.assertThat(consulting2.getId()).isNotIn((Object[])ids);
		Assertions.assertThat(consulting3.getId()).isIn((Object[])ids);
	}

	// 들어온 상담 요청 승인 test
	@Test
	void testPutPbApproveReserve() throws Exception {
		// 승인되지 않은 상담 요청의 ID
		Long idToApprove = consulting1.getId();

		// 1. PUT 요청 전에 상담 요청이 승인되지 않았음을 확인
		mockMvc.perform(MockMvcRequestBuilders.get(baseUrl + "?status=false"))
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(result -> {
				String responseBody = result.getResponse().getContentAsString();

				ResponseReserveDTO[] getApprovedReserves = objectMapper.readValue(responseBody,
					ResponseReserveDTO[].class);
				Assertions.assertThat(getApprovedReserves).isNotNull();
				Assertions.assertThat(Arrays.stream(getApprovedReserves)
					.anyMatch(reserve -> reserve.getId().equals(idToApprove))).isTrue();
			});

		// 2. PUT 요청을 보내 상담 요청을 승인 상태로 변경
		mockMvc.perform(MockMvcRequestBuilders.put(baseUrl + "?id=" + idToApprove))
			.andExpect(MockMvcResultMatchers.status().isOk());

		// 3. PUT 요청 후 상담 요청이 승인되었는지 확인
		mockMvc.perform(MockMvcRequestBuilders.get(baseUrl + "?status=true"))
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(result -> {
				String responseBody = result.getResponse().getContentAsString();

				ResponseReserveDTO[] getApprovedReserves = objectMapper.readValue(responseBody,
					ResponseReserveDTO[].class);
				Assertions.assertThat(getApprovedReserves).isNotNull();
				Assertions.assertThat(Arrays.stream(getApprovedReserves)
					.anyMatch(reserve -> reserve.getId().equals(idToApprove))).isTrue();
			});

		// 4. 이미 승인된 상담 요청을 다시 승인하려 할 경우, 500 오류가 발생
		mockMvc.perform(MockMvcRequestBuilders.put(baseUrl + "?id=" + consulting2.getId()))
			.andExpect(MockMvcResultMatchers.status().is5xxServerError());

		// 5. 존재하지 않는 ID에 대한 PUT 요청 시 500 오류 발생
		mockMvc.perform(MockMvcRequestBuilders.put(baseUrl + "?id=99999"))
			.andExpect(MockMvcResultMatchers.status().is5xxServerError());
	}

	@Test
	void testGetReservesByDate_ReturnsApprovedConsulting() throws Exception {

		LocalDate date = LocalDate.of(2024, 03, 07);
		Long pbId = pb.getId();

		mockMvc.perform(MockMvcRequestBuilders.get(baseUrl)
				.param("date", date.toString())
				.param("pbId", pbId.toString()))
			.andDo(MockMvcResultHandlers.print())
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(1))
			.andExpect(MockMvcResultMatchers.jsonPath("$[0].consultingId").value(consulting2.getId()))
			.andExpect(MockMvcResultMatchers.jsonPath("$[0].consultingDate").value(date.toString()))
			.andExpect(MockMvcResultMatchers.jsonPath("$[0].consultingTime").value("15:00:00"))
			.andExpect(MockMvcResultMatchers.jsonPath("$[0].category").value("Finance"))
			.andExpect(MockMvcResultMatchers.jsonPath("$[0].vipName").value("Test Customer"));
	}
}
