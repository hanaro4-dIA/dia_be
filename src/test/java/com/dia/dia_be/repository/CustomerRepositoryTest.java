package com.dia.dia_be.repository;

import com.dia.dia_be.domain.Customer;
import com.dia.dia_be.dto.pb.CustomerDTO;
import com.dia.dia_be.exception.GlobalException;
import com.dia.dia_be.exception.PbErrorCode;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

import jakarta.transaction.Transactional;

@Transactional
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE) // 실제 DB 사용 문제가 된다면 다른 DB나 여기서 BeforeEach로 수정할게요!
@DataJpaTest
public class CustomerRepositoryTest {

	@Autowired
	private CustomerRepository customerRepository;

	@Test
	public void testCreateCustomer() {
		Optional<Customer> foundCustomer = customerRepository.findById(1L);
		assertThat(foundCustomer).isPresent();

		CustomerDTO customerDto = foundCustomer.get().toDto();

		assertThat(customerDto.getName()).isEqualTo("강재준");
		assertThat(customerDto.getPbId()).isEqualTo(1L); // Pb_id
		assertThat(customerDto.getEmail()).isEqualTo("email1@example.com");
		assertThat(customerDto.getPassword()).isEqualTo("password1");
		assertThat(customerDto.getAddress()).isEqualTo("서울특별시 강남구");
		assertThat(customerDto.getTel()).isEqualTo("010-9945-5020");
		assertThat(customerDto.getCount()).isEqualTo(10);
		assertThat(customerDto.getMemo()).isEqualTo("강남구 거주, 안정적 자산 관리 필요.");
		assertThat(customerDto.getDate()).isEqualTo(LocalDate.of(2023, 10, 1));
	}

	@Test
	public void testRemoveCustomer() {

		Customer customer = customerRepository.findById(1L).orElseThrow(() -> new GlobalException(PbErrorCode.CUSTOMER_NOT_FOUND));

		customerRepository.deleteById(customer.getId());

		Optional<Customer> foundCustomer = customerRepository.findById(customer.getId());

		assertThat(foundCustomer).isNotPresent();
	}
}
