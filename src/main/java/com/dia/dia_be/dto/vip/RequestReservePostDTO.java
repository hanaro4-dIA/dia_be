package com.dia.dia_be.dto.vip;

import java.time.LocalDate;
import java.time.LocalTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RequestReservePostDTO {
	private LocalDate date;
	private LocalTime time;
	private Long categoryId;
	private String title;
	private String content;
}
