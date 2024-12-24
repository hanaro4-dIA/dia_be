package com.dia.dia_be.repository;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.transaction.annotation.Transactional;

import com.dia.dia_be.domain.Category;
import com.dia.dia_be.domain.Consulting;
import com.dia.dia_be.domain.Customer;
import com.dia.dia_be.domain.Journal;
import com.dia.dia_be.domain.Pb;
import com.dia.dia_be.service.vip.impl.VipReserveServiceImpl;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Transactional
public class ConsultingRepositoryTest {
	@Autowired
	ConsultingRepository consultingRepository;

	@Autowired
	PbRepository pbRepository;

	@Autowired
	JournalRepository journalRepository;

	@Autowired
	CustomerRepository customerRepository;

	@Autowired
	CategoryRepository categoryRepository;

	@MockBean
	VipReserveServiceImpl vipReserveService;

	Category category;
	Pb pb;
	Journal journal;
	Customer customer;

	@BeforeEach
	void setUp() {
		category = categoryRepository.findById(1L).get();
		pb = pbRepository.findById(1L).get();
		journal = journalRepository.findById(1L).get();
		customer = customerRepository.findById(1L).get();
		//승인된 Consulting
		Consulting approvedConsulting = Consulting.create(
			category,
			customer,
			"Approved Consulting",
			LocalDate.of(2024, 12, 20),
			LocalTime.of(14, 0),
			LocalDate.now(),
			LocalTime.now(),
			"Content for approved consulting",
			true // approve = true
		);
		consultingRepository.save(approvedConsulting);

		//승인되지 않은 Consulting
		Consulting notApprovedConsulting = Consulting.create(
			category,
			customer,
			"Not Approved Consulting",
			LocalDate.of(2024, 12, 21),
			LocalTime.of(15, 0),
			LocalDate.now(),
			LocalTime.now(),
			"Content for not approved consulting",
			false // approve = false
		);
		consultingRepository.save(notApprovedConsulting);

	}

	@Test
	void save() {
		Consulting consulting = Consulting.create(category, customer, "test", LocalDate.now(), LocalTime.now(),
			LocalDate.now(), LocalTime.now(), "content", false);

		Consulting testConsulting = consultingRepository.save(consulting);

		Assertions.assertThat(testConsulting.getId()).isNotNull();
		Assertions.assertThat(testConsulting.getCategory()).isEqualTo(category);
		Assertions.assertThat(testConsulting.getCustomer()).isEqualTo(customer);
		Assertions.assertThat(testConsulting).isIn(category.getConsulting());
		Assertions.assertThat(testConsulting).isIn(customer.getConsulting());
		Assertions.assertThat(testConsulting.isApprove()).isFalse();
	}

	@Test
	void delete() {
		Consulting consulting = Consulting.create(category, customer, "test", LocalDate.now(), LocalTime.now(),
			LocalDate.now(), LocalTime.now(), "content", false);
		Consulting saveConsulting = consultingRepository.save(consulting);
		Assertions.assertThat(saveConsulting.getId()).isNotNull();

		consultingRepository.deleteById(saveConsulting.getId());
		Optional<Consulting> testConsulting = consultingRepository.findById(saveConsulting.getId());

		Assertions.assertThat(testConsulting.isPresent()).isFalse();
		Assertions.assertThat(testConsulting).isNotIn(category.getConsulting());
		Assertions.assertThat(testConsulting).isNotIn(customer.getConsulting());
	}

	@Test
	@DisplayName("approve 안 된 상담만 가져오는지 테스트")
	void getApprovedConsultingTest() {
		List<Consulting> notApprovedConsultings = consultingRepository.findConsultingsByApprove(false);

		Assertions.assertThat(notApprovedConsultings.stream().allMatch(Consulting::isApprove)).isFalse();
	}

	@Test
	void updateTitleAndCategoryIdTest() throws Exception {
		String newTitle = "테스트용 상담 타이틀";
		Long newCategoryId = 2L;
		consultingRepository.updateTitleAndCategory(1L, newTitle, newCategoryId);

		Consulting consulting = consultingRepository.findById(1L).get();

		Assertions.assertThat(consulting.getCategory().getId()).isEqualTo(newCategoryId);
		Assertions.assertThat(consulting.getTitle()).isEqualTo(newTitle);
	}

	@Test
	@DisplayName("특정 날짜와 PB ID에 대해 승인된 상담만 가져오는지 테스트")
	void findByHopeDateAndApproveTrueAndCustomer_Pb_IdTest() {

		LocalDate hopeDate = LocalDate.of(2024, 12, 20);
		Long pbId = pb.getId();

		List<Consulting> result = consultingRepository.findByHopeDateAndApproveTrueAndCustomer_Pb_Id(hopeDate, pbId);

		// Then
		Assertions.assertThat(result).isNotNull();
		Assertions.assertThat(result).hasSize(1);
		Consulting consulting = result.get(0);
		Assertions.assertThat(consulting.getTitle()).isEqualTo("Approved Consulting");
		Assertions.assertThat(consulting.getHopeDate()).isEqualTo(hopeDate);
		Assertions.assertThat(consulting.isApprove()).isTrue();
		Assertions.assertThat(consulting.getCustomer().getPb().getId()).isEqualTo(pbId);
	}

	@Test
	@DisplayName("hopeDate가 미래이거나 상담일지 작성 완료 전인 리스트 불러오기")
	void getUpcomingConsultingsTest() {
		// hopeDate가 미래인 Consulting 추가
		Consulting futureConsulting = Consulting.create(category, customer, "test", LocalDate.now().plusDays(1),
			LocalTime.now(),
			LocalDate.now().plusDays(1), LocalTime.now(), "content", true);
		consultingRepository.save(futureConsulting);

		Consulting pastConsulting = Consulting.create(category, customer, "test", LocalDate.now().minusDays(1),
			LocalTime.now(),
			LocalDate.now().minusDays(1), LocalTime.now(), "content", true);
		consultingRepository.save(pastConsulting);
		Journal beforeJournal = pastConsulting.getJournal().update("not finish", false);
		Journal journal = journalRepository.save(beforeJournal);
		List<Consulting> notCompletedConsultings = consultingRepository.findByApproveAndJournal_Complete(true, false);
		Assertions.assertThat(notCompletedConsultings).isNotEmpty();
		Assertions.assertThat(notCompletedConsultings.stream().allMatch(Consulting::isApprove)).isTrue();
		Assertions.assertThat(notCompletedConsultings.contains(beforeJournal));
	}

	@Test
	void getReserveByIdIfNotApproved_shouldReturnReserve() {
		// Given: 데이터 가져오기
		Optional<Consulting> consultingOptional = consultingRepository.findById(11L);

		// 데이터가 없으면 테스트 실패
		assertTrue(consultingOptional.isPresent(), "Consulting with ID 11L not found in the database.");
		Consulting consulting = consultingOptional.get();

		// 데이터 확인
		System.out.println("Fetched Consulting ID: " + consulting.getId());
		System.out.println("Fetched Consulting Title: " + consulting.getTitle());

		// When
		Optional<Consulting> result = consultingRepository.findByIdAndApproveFalse(consulting.getId());

		// Then
		assertTrue(result.isPresent(), "Expected consulting to be present, but it was not.");
		assertEquals(consulting.getTitle(), result.get().getTitle());
	}

}
