package com.dia.dia_be.dto.pb.journalDTO;

import java.util.List;

import com.dia.dia_be.dto.pb.keywordDTO.ResponseFlaskKeywordDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ScriptListWithKeywordsResponseDTO {
	List<ScriptResponseDTO> scriptResponseDTOList;
	List<ResponseFlaskKeywordDTO> responseFlaskKeywordDTOList;

	public static ScriptListWithKeywordsResponseDTO of(List<ScriptResponseDTO> scriptResponseDTOList,
		List<ResponseFlaskKeywordDTO> responseFlaskKeywordDTOList) {
		return ScriptListWithKeywordsResponseDTO.builder()
			.scriptResponseDTOList(scriptResponseDTOList)
			.responseFlaskKeywordDTOList(responseFlaskKeywordDTOList)
			.build();
	}
}
