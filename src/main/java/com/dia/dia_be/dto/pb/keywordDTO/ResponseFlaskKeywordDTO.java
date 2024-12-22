package com.dia.dia_be.dto.pb.keywordDTO;

import com.dia.dia_be.domain.Keyword;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ResponseFlaskKeywordDTO {
	private Long id;
	private String title;
	private String content;

	public static ResponseKeywordDTO from(Keyword keyword) {
		return ResponseKeywordDTO.builder()
			.id(keyword.getId())
			.title(keyword.getTitle())
			.content(keyword.getContent())
			.build();
	}
}
