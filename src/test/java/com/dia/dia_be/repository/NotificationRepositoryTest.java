package com.dia.dia_be.repository;

import static org.assertj.core.api.Assertions.*;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import com.dia.dia_be.domain.Customer;
import com.dia.dia_be.domain.Notification;
import com.dia.dia_be.domain.Pb;

import jakarta.transaction.Transactional;

@DataJpaTest
@Transactional
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class NotificationRepositoryTest {

	@Autowired
	private TestEntityManager entityManager;

	@Autowired
	private NotificationRepository notificationRepository;

	private Customer testCustomer;

	@BeforeEach
	public void setUp() {
		Pb pb = Pb.create("password", "PB Name", "image.url", "Introduce", "Office", "Career", "loginId",
			"010-1234-5678", true);
		entityManager.persist(pb);

		testCustomer = Customer.create(pb, LocalDate.now(), null, 0, "Memo", "customer@email.com", "password",
			"Customer Name", "010-9876-5432", "Address");
		entityManager.persist(testCustomer);
	}

	@Test
	public void testCreateNotification() {
		Notification notification = Notification.create(testCustomer, "Test Title", "Test Content", LocalDate.now(),
			false);
		Notification savedNotification = notificationRepository.save(notification);

		assertThat(savedNotification).isNotNull();
		assertThat(savedNotification.getId()).isNotNull();
		assertThat(savedNotification.getTitle()).isEqualTo("Test Title");
		assertThat(savedNotification.getCustomer()).isEqualTo(testCustomer);
	}

	@Test
	public void testDeleteNotification() {
		Notification notification = Notification.create(testCustomer, "Delete Test", "Delete Content", LocalDate.now(),
			false);
		Notification savedNotification = notificationRepository.save(notification);

		notificationRepository.deleteById(savedNotification.getId());

		assertThat(notificationRepository.findById(savedNotification.getId())).isEmpty();
	}
}
