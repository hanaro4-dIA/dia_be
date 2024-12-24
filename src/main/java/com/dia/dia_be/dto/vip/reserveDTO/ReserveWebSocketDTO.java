package com.dia.dia_be.dto.vip.reserveDTO;

import java.time.LocalDate;
import java.time.LocalTime;

import com.dia.dia_be.domain.Consulting;

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
public class ReserveWebSocketDTO {
	private Long id;

	private String title;

	private LocalDate hopeDate;

	private LocalTime hopeTime;

	private LocalDate reserveDate;

	private LocalTime reserveTime;

	private String content;

	private boolean approve;

	private Long categoryId;

	private Long customerId;
	private String customerName;

	public static ReserveWebSocketDTO from(Consulting consulting) {
		return ReserveWebSocketDTO.builder()
			.id(consulting.getId())
			.title(consulting.getTitle())
			.hopeDate(consulting.getHopeDate())
			.hopeTime(consulting.getHopeTime())
			.reserveDate(consulting.getReserveDate())
			.reserveTime(consulting.getReserveTime())
			.content(consulting.getContent())
			.approve(consulting.isApprove())
			.categoryId(consulting.getCategory().getId())
			.customerId(consulting.getCustomer().getId())
			.customerName(consulting.getCustomer().getName())
			.build();
	}

}
