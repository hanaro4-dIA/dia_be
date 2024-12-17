package com.dia.dia_be.dto.vip;

import com.dia.dia_be.domain.Script;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ResponseJournalScriptDTO {
	private int sequence;
	private String speacker;
	private String content;

	public static ResponseJournalScriptDTO from(Script script) {
		return ResponseJournalScriptDTO.builder()
			.sequence(script.getScriptSequence())
			.speacker(script.getSpeaker())
			.content(script.getContent())
			.build();
	}
}
