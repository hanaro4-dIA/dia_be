package com.dia.dia_be.repository;

import static org.assertj.core.api.AssertionsForClassTypes.*;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.dia.dia_be.domain.Journal;

@SpringBootTest
public class JournalRepositoryTest {

	@Autowired
	JournalRepository journalRepository;

	@Test
	@DisplayName("findAll test")
	void findAllTest(){
		List<Journal> journals = journalRepository.findAll();
		assertThat(journals.size()).isGreaterThan(0);
	}

	@Test
	@DisplayName("findById test")
	void findByIdTest(){
		Journal journal = journalRepository.findById(1L).orElseThrow();
		assertThat(journal.getContents()).isNotEmpty();
		assertThat(journal.getComplete()).isNotNull();
	}
}
