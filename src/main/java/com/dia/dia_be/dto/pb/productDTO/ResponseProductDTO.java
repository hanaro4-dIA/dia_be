package com.dia.dia_be.dto.pb.productDTO;

import com.dia.dia_be.domain.Journal;
import com.dia.dia_be.domain.Product;
import com.dia.dia_be.dto.pb.journalDTO.ResponseJournalDTO;
import com.dia.dia_be.dto.pb.profileDTO.RequestProfileDTO;

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
	private String url;

	public static ResponseProductDTO from(Product product) {
		return ResponseProductDTO.builder()
			.id(product.getId())
			.productName(product.getName())
			.url(product.getProduct_url())
			.build();
	}
}
