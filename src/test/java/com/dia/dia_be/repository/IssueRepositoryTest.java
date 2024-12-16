package com.dia.dia_be.repository;

import static org.assertj.core.api.Assertions.*;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.dia.dia_be.domain.Issue;
import com.dia.dia_be.domain.Keyword;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class IssueRepositoryTest {
	@Autowired
	private IssueRepository issueRepository;

	@Autowired
	private KeywordRepository keywordRepository;

	private Keyword keyword;

	@BeforeEach
	void setUp() {
		keyword = keywordRepository.findById(1L).get();
	}

	@Test
	void createIssueTest() {
		Issue issue = Issue.create(keyword, "Test Issue", "http://test-issue.com", "http://test-image.com");

		Issue savedIssue = issueRepository.save(issue);

		assertThat(savedIssue.getId()).isNotNull();
		assertThat(savedIssue.getTitle()).isEqualTo("Test Issue");
		assertThat(savedIssue.getIssueUrl()).isEqualTo("http://test-issue.com");
		assertThat(savedIssue.getImageUrl()).isEqualTo("http://test-image.com");
		assertThat(savedIssue.getKeyword()).isEqualTo(keyword);
	}

	@Test
	void deleteIssueTest() {
		Issue issue = Issue.create(keyword, "Test Issue", "http://test-issue.com", "http://test-image.com");

		Issue savedIssue = issueRepository.save(issue);

		issueRepository.delete(savedIssue);

		Optional<Issue> deletedIssue = issueRepository.findById(savedIssue.getId());
		assertThat(deletedIssue).isEmpty();
	}
}
