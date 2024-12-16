package com.dia.dia_be.repository;

import java.util.List;
import java.util.stream.StreamSupport;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Sort;

import com.dia.dia_be.domain.Consulting;
import com.dia.dia_be.domain.Customer;
import com.dia.dia_be.domain.Journal;
import com.dia.dia_be.domain.JournalKeyword;
import com.dia.dia_be.domain.JournalProduct;
import com.dia.dia_be.domain.QConsulting;
import com.dia.dia_be.domain.QJournalKeyword;

import jakarta.persistence.EntityManager;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class JournalRepositoryTest {
	@Autowired
	private EntityManager em;

	@Autowired
	ConsultingRepository consultingRepository;

	@Autowired
	CustomerRepository customerRepository;

	@Autowired
	PbRepository pbRepository;
	@Autowired
	private JournalRepository journalRepository;
	@Autowired
	private JournalKeywordRepository journalKeywordRepository;
	@Autowired
	private KeywordRepository keywordRepository;
	@Autowired
	private JournalProductRepository journalProductRepository;
	@Autowired
	private ProductRepository productRepository;

	@Test
	void findJournalByConsultingIdTest() {
		Consulting consulting = consultingRepository.findById(1L).orElseThrow();
		Journal journal = consulting.getJournal();
		System.out.println(journal.getId());
		System.out.println(journal.getJournalKeyword());
		System.out.println(journal.getJournalProduct());
		System.out.println(journal.getContents());
		journal.getScript().forEach(script -> {
			System.out.println(script.getScriptSequence() + script.getSpeaker() + script.getContent());
		});
		Assertions.assertThat(journal.getConsulting()).isEqualTo(consulting);
		Assertions.assertThat(journal.isComplete()).isTrue();

	}

	@Test
	void findJournalByCustomerIdTest() {
		Customer customer = customerRepository.findById(1L).orElseThrow();
		QConsulting qConsulting = QConsulting.consulting;
		Iterable<Consulting> consultings = consultingRepository.findAll(
			qConsulting.customer.id.eq(customer.getId()), Sort.by(Sort.Direction.ASC, "id")
		);
		List<Consulting> consultingList = StreamSupport.stream(consultings.spliterator(), false).toList();
		List<Journal> journals = consultingList.stream().map(Consulting::getJournal).toList();
		for (int i = 0; i < consultingList.size(); i++) {
			Assertions.assertThat(journals.get(i).getConsulting()).isEqualTo(consultingList.get(i));
		}
	}

	@Test
	void updateJournalTest() {
		Customer customer = customerRepository.findById(1L).orElseThrow();
		QJournalKeyword qJournalKeyword = QJournalKeyword.journalKeyword;
		Journal journal = customer.getConsulting().get(0).getJournal();

		// journal entity 내부 수정 테스트
		journal.update(false);
		journal.update("수정!!");
		journalRepository.save(journal);
		Assertions.assertThat(journalRepository.findById(1L).orElseThrow()).isEqualTo(journal);

		// journal keyword 추가 테스트
		int beforeJK = journal.getJournalKeyword().size();

		JournalKeyword jkToAdd = journalKeywordRepository.save(
			JournalKeyword.create(keywordRepository.findById(1L).orElseThrow(), journal, customer));
		journal = customer.getConsulting().get(0).getJournal();
		int afterJK = journal.getJournalKeyword().size();
		Assertions.assertThat(afterJK).isGreaterThan(beforeJK);
		Assertions.assertThat(journal.getJournalKeyword())
			.contains(journalKeywordRepository.findById(jkToAdd.getId()).orElseThrow());

		// journal keyword 삭제 테스트
		journal.getJournalKeyword().remove(jkToAdd);
		journalRepository.save(journal);
		Assertions.assertThat(journal.getJournalKeyword().size()).isEqualTo(beforeJK);
		Assertions.assertThat(journal.getJournalKeyword()).doesNotContain(jkToAdd);

		// journal product 추가 테스트
		int beforeJP = journal.getJournalProduct().size();

		JournalProduct jpToAdd = journalProductRepository.save(
			JournalProduct.create(productRepository.findById(2L).orElseThrow(), journal));
		journal = customer.getConsulting().get(0).getJournal();
		int afterJP = journal.getJournalProduct().size();
		Assertions.assertThat(afterJP).isGreaterThan(beforeJP);
		Assertions.assertThat(journal.getJournalProduct()).contains(jpToAdd);

		// journal product 삭제 테스트
		journal.getJournalProduct().remove(jpToAdd);
		journalRepository.save(journal);
		Assertions.assertThat(journal.getJournalProduct().size()).isEqualTo(beforeJP);
		Assertions.assertThat(journal.getJournalProduct()).doesNotContain(jpToAdd);
	}

}
