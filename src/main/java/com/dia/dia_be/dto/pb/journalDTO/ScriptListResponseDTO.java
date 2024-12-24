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
public class ScriptListResponseDTO {
	List<ScriptResponseDTO> scriptResponseDTOList;

	public static ScriptListResponseDTO of(List<ScriptResponseDTO> scriptResponseDTOList){
		return ScriptListResponseDTO.builder().scriptResponseDTOList(scriptResponseDTOList).build();
	}
}
