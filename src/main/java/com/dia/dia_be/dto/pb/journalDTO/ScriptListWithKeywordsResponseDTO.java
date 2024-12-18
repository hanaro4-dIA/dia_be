package com.dia.dia_be.dto.pb.journalDTO;

import java.util.List;

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

	public static ScriptListWithKeywordsResponseDTO of(List<ScriptResponseDTO> scriptResponseDTOList){
		return ScriptListWithKeywordsResponseDTO.builder().scriptResponseDTOList(scriptResponseDTOList).build();
	}
}
