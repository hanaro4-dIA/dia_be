package com.dia.dia_be.dto.pb.productDTO;

import com.dia.dia_be.domain.Product;

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
public class ResponseProductDTO {
	private Long id;
	private String productName;
	private String productUrl;
	private String productImageUrl;

	public static ResponseProductDTO from(Product product) {
		return ResponseProductDTO.builder()
			.id(product.getId())
			.productName(product.getName())
			.productUrl(product.getProduct_url())
			.productImageUrl(product.getImage_url())
			.build();
	}
}
