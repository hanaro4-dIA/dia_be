package com.dia.dia_be.domain;

import jakarta.persistence.*;
import lombok.*;

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
	@JoinColumn(name = "customer_pb_id")
	private Customer_pb customer_pb;

	@Column(nullable = false, columnDefinition = "VARCHAR(30)")
	private String title;

	@Column(nullable = false, columnDefinition = "VARCHAR(100)")
	private String content;

	@Column(nullable = false, columnDefinition = "TIMESTAMP")
	private LocalTime date;

	@Column(nullable = false, columnDefinition = "TINYINT(1)")
	private Boolean is_read;

	public Notification(String title, String content, LocalTime date, Boolean is_read) {
		this.title = title;
		this.content = content;
		this.date = date;
		this.is_read = is_read;
	}

	@Builder
	public Notification create(Customer_pb customer_pb, String title, String content, LocalTime date, Boolean is_read) {
		Notification notification = new Notification(title, content, date, is_read);
		notification.addCustomer_pb(customer_pb);
		return notification;
	}

	private void addCustomer_pb(Customer_pb customer_pb) {
		this.customer_pb = customer_pb;
		customer_pb.getNotification().add(this);
	}


}
