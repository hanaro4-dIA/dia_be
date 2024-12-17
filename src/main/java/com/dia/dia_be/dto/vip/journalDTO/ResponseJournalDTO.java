package com.dia.dia_be.dto.vip.journalDTO;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ResponseJournalDTO {
	private Long id;
	private String category;
	private LocalDate date;
	private LocalTime time;
	private String manager;
	private String contents;
	private List<Product> journalProducts;

	@Data
	@NoArgsConstructor
	@AllArgsConstructor
	public static class Product {
		private Long id;
		private String name;
		private String url;
	}
}
