package com.dia.dia_be.dto.pb.reservesDTO;

import java.time.LocalDate;
import java.time.LocalTime;

import com.dia.dia_be.domain.Consulting;
import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ResponseReserveDTO {
	private Long id;
	private String title;
	private String content;

	private LocalDate hopeDate;

	@JsonFormat(pattern = "HH:mm")
	private LocalTime hopeTime;
	private LocalDate reserveDate;

	@JsonFormat(pattern = "HH:mm")
	private LocalTime reserveTime;
	private boolean approve;
	private Long categoryId;
	private Long customerId;
	private String customerName;

	public static ResponseReserveDTO from(Consulting consulting) {
		return ResponseReserveDTO.builder()
			.id(consulting.getId())
			.title(consulting.getTitle())
			.content(consulting.getContent())
			.hopeDate(consulting.getHopeDate())
			.hopeTime(consulting.getHopeTime())
			.reserveDate(consulting.getReserveDate())
			.reserveTime(consulting.getReserveTime())
			.approve(consulting.isApprove())
			.categoryId(consulting.getCategory().getId())
			.customerId(consulting.getCustomer().getId())
			.customerName(consulting.getCustomer().getName())
			.build();
	}

}
