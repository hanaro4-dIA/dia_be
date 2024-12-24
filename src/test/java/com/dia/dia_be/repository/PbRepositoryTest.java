package com.dia.dia_be.repository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.dia.dia_be.domain.Pb;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class PbRepositoryTest {
	@Autowired
	PbRepository pbRepository;

	@Test
	void findByLoginIdAndPassword(){
		Pb pb = pbRepository.findAll().get(0);
		Pb testPb = pbRepository.findByLoginIdAndPassword(pb.getLoginId(),pb.getPassword()).get();

		Assertions.assertThat(testPb.getId()).isEqualTo(pb.getId());

	}
}
