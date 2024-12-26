package com.dia.dia_be.dto.pb.journalDTO;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import com.dia.dia_be.domain.Journal;
import com.dia.dia_be.dto.pb.productDTO.ResponseProductDTO;
import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ResponseJournalDTO {
	private Long id;
	private Long customerId;
	private boolean complete;
	private String categoryName;
	private LocalDate hopeDate;
	@JsonFormat(pattern = "HH:mm")
	private LocalTime hopeTime;
	private LocalDate consultDate;
	private String consultTitle;
	private String pbName;
	private String contents;
	private List<ResponseProductDTO> journalProducts;

	public static ResponseJournalDTO from(Journal journal) {
		return ResponseJournalDTO.builder()
			.id(journal.getId())
			.customerId(journal.getConsulting().getCustomer().getId())
			.complete(journal.isComplete())
			.categoryName(journal.getConsulting().getCategory().getName())
			.hopeDate(journal.getConsulting().getHopeDate())
			.hopeTime(journal.getConsulting().getHopeTime())
			.pbName(journal.getConsulting().getCustomer().getPb().getName())
			.consultTitle(journal.getConsulting().getTitle())
			.consultDate(journal.getConsulting().getReserveDate())
			.contents(journal.getContents())
			.journalProducts(journal.getJournalProduct()
				.stream()
				.map(journalProduct -> ResponseProductDTO.from(journalProduct.getProduct()))
				.toList())
			.build();
	}
}
