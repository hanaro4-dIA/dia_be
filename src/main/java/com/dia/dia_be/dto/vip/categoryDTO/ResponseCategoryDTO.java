package com.dia.dia_be.dto.vip.categoryDTO;

import com.dia.dia_be.domain.Category;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ResponseCategoryDTO {
	private Long id;
	private String name;

	public static ResponseCategoryDTO from(Category category) {
		return ResponseCategoryDTO.builder()
			.id(category.getId())
			.name(category.getName())
			.build();
	}
}
