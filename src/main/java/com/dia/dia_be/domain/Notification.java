package com.dia.dia_be.domain;

import java.time.LocalTime;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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

	@Builder
	public Notification(String title, String content, LocalTime date, boolean isRead, Customer customer) {
		this.title = title;
		this.content = content;
		this.date = date;
		this.isRead = isRead;
		this.customer = customer;
	}

	public Notification update(boolean isRead) {
		this.isRead = isRead;
		return this;
	}

	public void addCustomer(Customer customer) {
		this.customer = customer;
		customer.getNotification().add(this);
	}
}
