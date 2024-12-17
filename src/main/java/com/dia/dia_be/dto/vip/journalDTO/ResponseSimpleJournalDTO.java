package com.dia.dia_be.dto.vip.journalDTO;

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
public class ResponseSimpleJournalDTO {
	@Schema(description = "상담일지id", example = "1")
	private Long id;
	@Schema(description = "카테고리명", example = "은퇴설계")
	private String category;
	@Schema(description = "상담 제목", example = "퇴직연금에 가입하고 싶습니다.")
	private String title;
	@Schema(description = "상담일(희망일과 동일)", example = "2024-12-30")
	private LocalDate date;
	@Schema(description = "상담시(희망시와 동일)", example = "14:00")
	private LocalTime time;
	@Schema(description = "PB명", example = "안유진")
	private String manager;
	@Schema(description = "열람가능여부", example = "true")
	private boolean status;
}
