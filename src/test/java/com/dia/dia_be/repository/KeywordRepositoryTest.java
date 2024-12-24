package com.dia.dia_be.repository;


import static org.assertj.core.api.Assertions.*;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.dia.dia_be.domain.Keyword;

@SpringBootTest
public class KeywordRepositoryTest {

	@Autowired
	private KeywordRepository keywordRepository;

	String content;
	String title;
	String id;

	@BeforeEach
	void setUp(){
		content = "테스트를 위한 문장";
		title = "테스트를 위한 키워드";
	}

	@Test
	void save(){
		Keyword keyword = Keyword.builder().content(content).title(title).build();
		Keyword savedKeyword = keywordRepository.save(keyword);
		assertThat(savedKeyword.getId()).isNotNull();

		String savedTitle = savedKeyword.getTitle();
		String savedContent = savedKeyword.getContent();

		assertThat(savedTitle).isEqualTo(title);
		assertThat(savedContent).isEqualTo(content);
	}

	@Test
	void delete(){
		Keyword keyword = Keyword.builder().content(content).title(title).build();
		Keyword savedKeyword = keywordRepository.save(keyword);
		assertThat(savedKeyword.getId()).isNotNull();

		keywordRepository.deleteById(savedKeyword.getId());
		Optional<Keyword> deletedKeyword = keywordRepository.findById(savedKeyword.getId());

		assertThat(deletedKeyword).isNotPresent();
	}

	@Test
	void findAllKeywordsTest(){
		List<Keyword> keywords = keywordRepository.findAll();

		assertThat(keywords.stream().noneMatch(keyword -> keyword.getTitle().isEmpty())).isTrue();
		assertThat(keywords.stream().noneMatch(keyword -> keyword.getContent().isEmpty())).isTrue();
	}

	@Test
	void findByIdKeywordTest(){
		Long id = 1L;
		Keyword keyword = keywordRepository.findById(id).orElseThrow();

		assertThat(keyword.getTitle()).isNotEmpty();
		assertThat(keyword.getContent()).isNotEmpty();
	}
}
