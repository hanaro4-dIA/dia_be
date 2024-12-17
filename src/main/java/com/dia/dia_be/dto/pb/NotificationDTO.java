package com.dia.dia_be.dto.pb;

import java.time.LocalDate;
import java.util.List;

import com.dia.dia_be.domain.Customer;
import com.dia.dia_be.domain.Notification;
import com.dia.dia_be.exception.GlobalException;
import com.dia.dia_be.exception.PbErrorCode;
import com.dia.dia_be.repository.CustomerRepository;
import com.fasterxml.jackson.annotation.JsonInclude;

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
public class NotificationDTO {

	private Long id;
	private Long customerId;

	@Schema(hidden = true)
	@JsonInclude(JsonInclude.Include.NON_NULL) // 여러명한테 보내는거 때문에 이를 받을 배열이 필요했는데, 이를 처리하고 null은 나오지 않게 처리하는 것!!
	private List<Long> customerIds;
	private String title;
	private String content;
	private LocalDate date;
	private boolean isRead = false;

	public NotificationDTO(Long id, List<Long> customerIds, String title, String content, LocalDate date, boolean isRead) {
		this.id = id;
		this.customerId = customerIds.get(0);
		this.title = title;
		this.content = content;
		this.date = date;
		this.isRead = false;
	}

	public static Notification toEntity(NotificationDTO notificationDTO, CustomerRepository customerRepository) {
		Customer customer = customerRepository.findById(notificationDTO.getCustomerId())
			.orElseThrow(() -> new GlobalException(PbErrorCode.CUSTOMER_NOT_FOUND));

		return Notification.create(
			customer,
			notificationDTO.getTitle(),
			notificationDTO.getContent(),
			notificationDTO.getDate(),
			notificationDTO.isRead()
		);
	}

	public static NotificationDTO toDto(Notification notification) {
		return NotificationDTO.builder()
			.id(notification.getId())
			.customerId(notification.getCustomer().getId())
			.title(notification.getTitle())
			.content(notification.getContent())
			.date(notification.getDate())
			.isRead(notification.isRead())
			.build();
	}
}
