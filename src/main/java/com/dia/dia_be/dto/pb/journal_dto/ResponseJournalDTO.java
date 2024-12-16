package com.dia.dia_be.dto.pb.journal_dto;

import java.time.LocalDate;

import com.dia.dia_be.domain.Journal;

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
	private String pbName;
	private String consultTitle;
	private LocalDate consultDate;

	public static ResponseJournalDTO from(Journal journal){
		return ResponseJournalDTO.builder()
			.id(journal.getId())
			.pbName(journal.getConsulting().getCustomer().getPb().getName())
			.consultTitle(journal.getConsulting().getTitle())
			.consultDate(journal.getConsulting().getReserveDate())
			.build();
	}
}
