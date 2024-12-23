package com.dia.dia_be.dto.pb.journalDTO;

import com.dia.dia_be.domain.Script;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ScriptRequestDTO {
	private Long scriptId;
	private int scriptSequence;
	private String speaker;
	private String content;

	public static ScriptRequestDTO from(Script script){
		return ScriptRequestDTO.builder()
			.scriptId(script.getId())
			.scriptSequence(script.getScriptSequence())
			.speaker(script.getSpeaker().toString())
			.content(script.getContent())
			.build();
	}
}
