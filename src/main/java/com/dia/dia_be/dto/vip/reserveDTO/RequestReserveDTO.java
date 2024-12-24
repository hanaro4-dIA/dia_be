package com.dia.dia_be.dto.vip.reserveDTO;

import java.time.LocalDate;
import java.time.LocalTime;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RequestReserveDTO {
	@Schema(description = "상담희망일", example = "2024-12-30")
	private LocalDate date;
	@Schema(description = "상담희망시", example = "14:00")
	private LocalTime time;
	@Schema(description = "카테고리ID", example = "2")
	private Long categoryId;
	@Schema(description = "상담 제목", example = "퇴직연금에 가입하고 싶어요.")
	private String title;
	@Schema(description = "상담 내용", example = "퇴직이 가까워져옵니다...")
	private String content;
}
