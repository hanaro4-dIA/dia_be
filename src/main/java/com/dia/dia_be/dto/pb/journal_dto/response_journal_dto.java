package com.dia.dia_be.dto.pb.journal_dto;

import java.time.LocalDate;

import com.dia.dia_be.domain.Journal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class response_journal_dto {
	private Long id;
	private String pb_name;
	private String consult_title;
	private LocalDate consult_date;

	public static response_journal_dto from(Journal journal){
		return response_journal_dto.builder()
			.id(journal.getId())
			.pb_name(journal.getConsulting().getCustomer().getPb().getName())
			.consult_title(journal.getConsulting().getTitle())
			.consult_date(journal.getConsulting().getReserve_date())
			.build();
	}
}
