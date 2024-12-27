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
public class ResponseListJournalKeywordDTO {
	List<ResponseKeywordDTO> keywordDTOList;

	public static ResponseListJournalKeywordDTO of(List<ResponseKeywordDTO> keywordDTOList) {
		return ResponseListJournalKeywordDTO.builder().keywordDTOList(keywordDTOList).build();
	}
}
