package com.dia.dia_be.dto.pb.notificationDTO;

import java.time.LocalDate;

import com.dia.dia_be.domain.Customer;
import com.dia.dia_be.domain.Notification;
import com.dia.dia_be.exception.GlobalException;
import com.dia.dia_be.exception.PbErrorCode;
import com.dia.dia_be.repository.CustomerRepository;

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
public class ResponseNotificationDTO {

	private Long id;
	private Long customerId;
	private String title;
	private String content;
	private LocalDate date;
	private boolean isRead;

	public static ResponseNotificationDTO of(Notification notification) {
		return ResponseNotificationDTO.builder()
			.id(notification.getId())
			.customerId(notification.getCustomer().getId())
			.title(notification.getTitle())
			.content(notification.getContent())
			.date(notification.getDate())
			.isRead(notification.isRead())
			.build();
	}

	public static Notification fromDTO(ResponseNotificationDTO dto, CustomerRepository customerRepository) {
		Customer customer = customerRepository.findById(dto.getCustomerId())
			.orElseThrow(() -> new GlobalException(PbErrorCode.CUSTOMER_NOT_FOUND));

		return Notification.create(
			customer,
			dto.getTitle(),
			dto.getContent(),
			dto.getDate(),
			dto.isRead()
		);
	}
}
