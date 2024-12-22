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
public class ScriptListRequestDTO {
	List<ScriptRequestDTO> scriptRequestDTOList;

	public static ScriptListRequestDTO of(List<ScriptRequestDTO> scriptRequestDTOList){
		return ScriptListRequestDTO.builder().scriptRequestDTOList(scriptRequestDTOList).build();
	}
}
