package com.dia.dia_be.dto.pb.journalDTO;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RequestJournalDTO {
	private Long consultingId;
	private Long categoryId;
	private String consultingTitle;
	private String journalContents;
	// pb의 추천 상품 키들
	private List<Long> recommendedProductsKeys;
}
