package com.dia.dia_be.dto.vip.reserveDTO;

import java.time.LocalDate;
import java.time.LocalTime;

import com.dia.dia_be.domain.Consulting;
import com.fasterxml.jackson.annotation.JsonFormat;

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
public class ResponseReserveDTO {
	@Schema(description = "아이디", example = "2")
	private Long id;
	@Schema(description = "상담 제목", example = "퇴직연금에 가입하고 싶어요.")
	private String title;
	@Schema(description = "카테고리명", example = "은퇴설계")
	private String categoryName;
	@Schema(description = "상담희망일", example = "2024-12-30")
	@JsonFormat(pattern = "yyyy.MM.dd")
	private LocalDate date;
	@Schema(description = "상담희망시", example = "14:00")
	@JsonFormat(pattern = "HH:mm")
	private LocalTime time;
	@Schema(description = "상담 내용", example = "퇴직이 가까워져옵니다...")
	private String content;
	@Schema(description = "고객명", example = "강재준")
	private String customerName;
	@Schema(description = "PB명", example = "안유진")
	private String pbName;

	public static ResponseReserveDTO from(Consulting consulting) {
		return ResponseReserveDTO.builder()
			.id(consulting.getId())
			.title(consulting.getTitle())
			.categoryName(consulting.getCategory().getName())
			.date(consulting.getHopeDate())
			.time(consulting.getHopeTime())
			.content(consulting.getContent())
			.customerName(consulting.getCustomer().getName())
			.pbName(consulting.getCustomer().getPb().getName())
			.build();
	}
}
