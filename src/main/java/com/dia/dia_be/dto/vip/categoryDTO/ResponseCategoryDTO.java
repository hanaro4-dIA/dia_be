package com.dia.dia_be.dto.vip.categoryDTO;

import com.dia.dia_be.domain.Category;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ResponseCategoryDTO {
	@Schema(description = "아이디", example = "2")
	private Long id;
	@Schema(description = "카테고리명", example = "은퇴설계")
	private String name;

	public static ResponseCategoryDTO from(Category category) {
		return ResponseCategoryDTO.builder()
			.id(category.getId())
			.name(category.getName())
			.build();
	}
}
