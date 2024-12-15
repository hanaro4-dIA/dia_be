package com.dia.dia_be.repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.dia.dia_be.domain.Customer;
import com.dia.dia_be.domain.Notification;
import com.dia.dia_be.domain.Pb;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class NotificationRepositoryTest {

	@Autowired
	private NotificationRepository notificationRepository;

	@Autowired
	private CustomerRepository customerRepository;

	@Autowired
	private PbRepository pbRepository;

	private Customer customer;
	private Pb pb;

	@BeforeEach
	void setup() {
		pb = pbRepository.findById(1L).orElseThrow(() -> new IllegalStateException("PB not found"));
		customer = Customer.builder()
			.pb(pb)
			.date(LocalDate.now())
			.count(3)
			.memo("강남구 거주, 안정적 자산 관리 필요.")
			.email("user1@email.com")
			.password("testUser1234")
			.name("강재준")
			.tel("01012345678")
			.address("강남구")
			.build();
		customer = customerRepository.save(customer);
	}

	@Test
	void createNotification() {
		Notification notification = Notification.builder()
			.title("알림 제목")
			.content("알림 내용")
			.date(LocalTime.now())
			.isRead(false)
			.build();

		notification.addCustomer(customer);

		Notification savedNotification = notificationRepository.save(notification);

		Assertions.assertThat(savedNotification.getId()).isNotNull();
		Assertions.assertThat(savedNotification.getTitle()).isEqualTo("알림 제목");
		Assertions.assertThat(savedNotification.getCustomer()).isEqualTo(customer);
	}

	@Test
	void deleteNotification() {
		Notification notification = Notification.builder()
			.title("알림 제목")
			.content("알림 내용")
			.date(LocalTime.now())
			.isRead(false)
			.build();

		notification.addCustomer(customer);
		Notification savedNotification = notificationRepository.save(notification);

		notificationRepository.delete(savedNotification);
		Optional<Notification> deletedNotification = notificationRepository.findById(savedNotification.getId());

		Assertions.assertThat(deletedNotification).isEmpty();
	}
}
