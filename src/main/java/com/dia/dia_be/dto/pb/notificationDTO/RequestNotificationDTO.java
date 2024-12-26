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

	@Schema(hidden = true)
	private List<Long> customerIds;

	@Schema(description = "알림 제목", example = "알림 제목입니다.")
	private String title;
	@Schema(description = "알림 내용", example = "알림 내용입니다.")
	private String content;
	@Schema(description = "날짜", example = "2024-12-25")
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
