package com.dia.dia_be.dto.pb.journalDTO;

import com.dia.dia_be.domain.Script;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ScriptRequestDTO {
	@Schema(description = "스크립트ID", example = "1")
	private Long scriptId;
	@Schema(description = "스크립트 순서", example = "1")
	private int scriptSequence;
	@Schema(description = "발화자", example = "PB")
	private String speaker;
	@Schema(description = "내용", example = "수정된 스크립트 말풍선")
	private String content;

	public static ScriptRequestDTO from(Script script) {
		return ScriptRequestDTO.builder()
			.scriptId(script.getId())
			.scriptSequence(script.getScriptSequence())
			.speaker(script.getSpeaker().toString())
			.content(script.getContent())
			.build();
	}
}
