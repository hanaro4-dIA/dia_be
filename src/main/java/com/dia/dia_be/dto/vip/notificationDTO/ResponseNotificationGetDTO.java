package com.dia.dia_be.dto.vip.notificationDTO;

import com.dia.dia_be.domain.Notification;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ResponseNotificationGetDTO {
	@Schema(description = "아이디", example = "1")
	private Long id;
	@Schema(description = "손님 ID", example = "1")
	private Long customerId;
	@Schema(description = "알림 제목", example = "제목1")
	private String title;
	@Schema(description = "알림 내용", example = "내용1")
	private String text;
	@Schema(description = "읽음 여부", example = "false")
	private boolean isRead;

	public static ResponseNotificationGetDTO from(Notification notification) {
		return ResponseNotificationGetDTO.builder()
			.id(notification.getId())
			.customerId(notification.getCustomer().getId())
			.title(notification.getTitle())
			.text(notification.getContent())
			.isRead(notification.isRead())
			.build();
	}
}
