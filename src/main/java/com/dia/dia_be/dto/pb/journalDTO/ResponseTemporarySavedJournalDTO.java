package com.dia.dia_be.dto.pb.journalDTO;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import com.dia.dia_be.domain.Journal;
import com.dia.dia_be.domain.Product;
import com.dia.dia_be.dto.pb.productDTO.ResponseProductDTO;

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
public class ResponseTemporarySavedJournalDTO {
	private Long categoryId;
	private String consultingTitle;
	private LocalDate consultingDate;
	private LocalTime consultingTime;
	private String pbName;
	private String journalContents;
	private List<ResponseProductDTO> recommendedProduct;

	public static ResponseTemporarySavedJournalDTO from(Journal journal) {
		return ResponseTemporarySavedJournalDTO.builder()
			.categoryId(journal.getConsulting().getCategory().getId())
			.consultingTitle(journal.getConsulting().getTitle())
			.consultingDate(journal.getConsulting().getReserveDate())
			.consultingTime(journal.getConsulting().getReserveTime())
			.pbName(journal.getConsulting().getCustomer().getPb().getName())
			.journalContents(journal.getContents())
			.recommendedProduct(journal.getJournalProduct().stream().map(product ->
				ResponseProductDTO.from(product.getProduct())
			).toList())
			.build();
	}
}
