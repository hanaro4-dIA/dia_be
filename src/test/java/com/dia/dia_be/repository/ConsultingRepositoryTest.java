package com.dia.dia_be.repository;

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

import com.dia.dia_be.domain.Category;
import com.dia.dia_be.domain.Consulting;
import com.dia.dia_be.domain.Customer;
import com.dia.dia_be.domain.Journal;
import com.dia.dia_be.domain.Pb;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
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
}
