package com.dia.dia_be.domain;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalTime;

@Getter
@Setter(AccessLevel.PACKAGE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Notification {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(columnDefinition = "INT UNSIGNED")
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "customer_id")
	private Customer customer;

	@Column(nullable = false, updatable = false, columnDefinition = "VARCHAR(30)")
	private String title;

	@Column(nullable = false, updatable = false, columnDefinition = "TEXT")
	private String content;

	@CreationTimestamp
	@Column(nullable = false, updatable = false, columnDefinition = "timestamp default current_timestamp")
	private LocalTime date;

	@Column(nullable = false, columnDefinition = "TINYINT(1)")
	private boolean isRead;

	private Notification(String title, String content, LocalTime date, boolean isRead) {
		this.title = title;
		this.content = content;
		this.date = date;
		this.isRead = isRead;
	}

	@Builder
	public Notification create(Customer customer, String title, String content, LocalTime date, boolean isRead) {
		Notification notification = new Notification(title, content, date, isRead);
		notification.addCustomer(customer);
		return notification;
	}

	public Notification update(boolean isRead){
		this.isRead = isRead;
		return this;
	}

	private void addCustomer(Customer customer) {
		this.customer = customer;
		customer.getNotification().add(this);
	}


}
