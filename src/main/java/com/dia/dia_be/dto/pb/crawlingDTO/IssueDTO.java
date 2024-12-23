package com.dia.dia_be.dto.pb.crawlingDTO;

import com.dia.dia_be.domain.Issue;
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
public class IssueDTO {
	private String issueTitle;

	private String issueUrl;

	private String imageUrl;

	private Long keywordId;

	public static IssueDTO of(String issueTitle, String issueUrl, String imageUrl, Long keywordId) {
		return IssueDTO.builder()
			.issueTitle(issueTitle)
			.issueUrl(issueUrl)
			.imageUrl(imageUrl)
			.keywordId(keywordId)
			.build();
	}

	public Issue toEntity(Keyword keyword) {
		return Issue.create(
			keyword,
			this.issueTitle,
			this.issueUrl,
			this.imageUrl
		);
	}
}
