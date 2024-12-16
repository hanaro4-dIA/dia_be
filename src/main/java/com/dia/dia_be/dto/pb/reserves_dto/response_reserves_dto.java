package com.dia.dia_be.dto.pb.reserves_dto;

import java.time.LocalDate;
import java.time.LocalTime;

import com.dia.dia_be.domain.Category;
import com.dia.dia_be.domain.Consulting;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class response_reserves_dto {
	private Long id;
	private String title;
	private LocalDate hopeDate;
	private LocalTime hopeTime;
	private LocalDate reserveDate;
	private LocalTime reserveTime;
	private boolean approve;
	private Category category;

	public static response_reserves_dto from(Consulting consulting) {
		return response_reserves_dto.builder()
			.id(consulting.getId())
			.title(consulting.getTitle())
			.hopeDate(consulting.getHopeDate())
			.hopeTime(consulting.getHopeTime())
			.reserveDate(consulting.getReserveDate())
			.reserveTime(consulting.getReserveTime())
			.approve(consulting.isApprove())
			.category(consulting.getCategory())
			.build();
	}
}
