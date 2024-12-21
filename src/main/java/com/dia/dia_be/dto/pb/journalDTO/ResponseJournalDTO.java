package com.dia.dia_be.dto.pb.journalDTO;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import com.dia.dia_be.domain.Journal;
import com.dia.dia_be.domain.Product;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class ResponseJournalDTO {
	private Long id;
	private String pbName;
	private Long customerId;
	private String categoryName;
	private String consultTitle;
	private LocalDate consultDate;
	private String contents;
	private List<Product> journalProduct;

	public static ResponseJournalDTO from(Journal journal) {
		List<Product> journalProducts = journal.getJournalProduct().stream()
			.map(journalProduct -> new Product(
				journalProduct.getProduct().getName(),
				journalProduct.getProduct().getProduct_url(),
				journalProduct.getProduct().getImage_url()))
			.collect(Collectors.toList());

		return ResponseJournalDTO.builder()
			.id(journal.getId())
			.customerId(journal.getConsulting().getCustomer().getId())
			.pbName(journal.getConsulting().getCustomer().getPb().getName())
			.categoryName(journal.getConsulting().getCategory().getName())
			.consultTitle(journal.getConsulting().getTitle())
			.consultDate(journal.getConsulting().getReserveDate())
			.contents(journal.getContents())
			.journalProduct(journalProducts)
			.build();
	}

}
