package com.dia.dia_be.dto.pb.journalDTO;

import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RequestJournalDTO {
	@Schema(description = "상담ID", example = "1")
	private Long consultingId;
	@Schema(description = "카테고리ID", example = "2")
	private Long categoryId;
	@Schema(description = "상담 제목", example = "상담 제목 예시입니다.")
	private String consultingTitle;
	@Schema(description = "상담 내용", example = "상담 내용 예시입니다.")
	private String journalContents;
	// pb의 추천 상품 키들
	@Schema(description = "pb의 추천 상품 키들", example = "[1,2,3]")
	private List<Long> recommendedProductsKeys;
}
