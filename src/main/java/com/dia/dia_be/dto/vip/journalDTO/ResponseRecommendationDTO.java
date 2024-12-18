package com.dia.dia_be.dto.vip.journalDTO;

import com.dia.dia_be.domain.Issue;
import com.dia.dia_be.domain.Product;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ResponseRecommendationDTO {
	private Long id;
	private String imgUrl;
	private String description;
	private String url;

	public static ResponseRecommendationDTO from(Product product) {
		return ResponseRecommendationDTO.builder()
			.id(product.getId())
			.imgUrl(product.getImage_url())
			.description(product.getName())
			.url(product.getProduct_url())
			.build();
	}

	public static ResponseRecommendationDTO from(Issue issue) {
		return ResponseRecommendationDTO.builder()
			.id(issue.getId())
			.imgUrl(issue.getImageUrl())
			.description(issue.getTitle())
			.url(issue.getIssueUrl())
			.build();
	}
}
