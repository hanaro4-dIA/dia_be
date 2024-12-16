package com.dia.dia_be.dto.vip;

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
public class ResponseCategoryGetDTO {
	@Schema(description = "아이디", example = "2")
	private Long id;
	@Schema(description = "카테고리명", example = "은퇴설계")
	private String name;

	public static ResponseCategoryGetDTO from(Category category) {
		return ResponseCategoryGetDTO.builder()
			.id(category.getId())
			.name(category.getName())
			.build();
	}
}
