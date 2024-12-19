package com.dia.dia_be.dto.pb.journalDTO;

import java.util.List;

import com.dia.dia_be.dto.pb.keywordDTO.ResponseKeywordDTO;

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
	List<ResponseKeywordDTO> responseKeywordDTOList;

	public static ScriptListWithKeywordsResponseDTO of(List<ScriptResponseDTO> scriptResponseDTOList,
		List<ResponseKeywordDTO> responseKeywordDTOList) {
		return ScriptListWithKeywordsResponseDTO.builder()
			.scriptResponseDTOList(scriptResponseDTOList)
			.responseKeywordDTOList(responseKeywordDTOList)
			.build();
	}
}
