package com.dia.dia_be.repository;

import static org.assertj.core.api.Assertions.*;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.dia.dia_be.domain.Hashtag;
import com.dia.dia_be.domain.Pb;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class HashtagRepositoryTest {

	@Autowired
	private HashtagRepository hashtagRepository;

	@Autowired
	private PbRepository pbRepository;

	private Pb pb;

	@BeforeEach
	void setUp() {
		pb = pbRepository.findById(1L).get();
	}

	@Test
	void createHashtagTest() {
		Hashtag hashtag = Hashtag.create(pb, "TestHashtag");
		Hashtag savedHashtag = hashtagRepository.save(hashtag);

		Optional<Hashtag> retrievedHashtag = hashtagRepository.findById(savedHashtag.getId());
		assertThat(retrievedHashtag).isPresent();
		assertThat(retrievedHashtag.get().getName()).isEqualTo("TestHashtag");
		assertThat(retrievedHashtag.get().getPb().getName()).isEqualTo("손흥민");
	}

	@Test
	void removeHashtagTest() {
		Hashtag hashtag = Hashtag.create(pb, "TestHashtag");
		Hashtag savedHashtag = hashtagRepository.save(hashtag);

		hashtagRepository.delete(savedHashtag);

		Optional<Hashtag> deletedHashtag = hashtagRepository.findById(savedHashtag.getId());
		assertThat(deletedHashtag).isNotPresent();
	}

	void findAllByPbTest() {

		List<Hashtag> existingHashtags = hashtagRepository.findAllByPb(pb);
		hashtagRepository.deleteAll(existingHashtags);

		//해시태그 추가
		Hashtag hashtag1 = Hashtag.create(pb, "자산관리");
		Hashtag hashtag2 = Hashtag.create(pb, "금융컨설팅");
		Hashtag hashtag3 = Hashtag.create(pb, "포트폴리오");
		hashtagRepository.saveAll(List.of(hashtag1, hashtag2, hashtag3));

		//해시태그 조회
		List<Hashtag> retrievedHashtags = hashtagRepository.findAllByPb(pb);

		assertThat(retrievedHashtags).hasSize(3);
		assertThat(retrievedHashtags)
			.extracting(Hashtag::getName)
			.containsExactlyInAnyOrder("자산관리", "금융컨설팅", "포트폴리오");
	}
}
