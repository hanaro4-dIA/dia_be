package com.dia.dia_be.dto.pb.notificationDTO;

import java.time.LocalDate;
import java.util.List;

import com.dia.dia_be.domain.Customer;
import com.dia.dia_be.domain.Notification;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RequestNotificationDTO {

	@Schema(hidden = true) // Swagger에서 숨기기
	private List<Long> customerIds;

	private String title;
	private String content;
	private LocalDate date;

	public static RequestNotificationDTO of(Notification notification) {
		return RequestNotificationDTO.builder()
			.title(notification.getTitle())
			.content(notification.getContent())
			.date(notification.getDate())
			.build();
	}

	public Notification toEntity(Customer customer) {
		return Notification.create(
			customer,
			this.title,
			this.content,
			this.date,
			false
		);
	}
}
