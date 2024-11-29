package com.dia.dia_be.domain;

import jakarta.persistence.*;
import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Issue {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(columnDefinition = "INT UNSIGNED")
	private Long id;

	@Column(nullable = false, columnDefinition = "VARCHAR(100)")
	private String title;

	@Column(nullable = false, columnDefinition = "VARCHAR(250)")
	private String issue_url;

	@Column(nullable = false, columnDefinition = "VARCHAR(250)")
	private String image_url;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "keyword_id", nullable = false)
	private Keyword keyword;

	private Issue(String title, String issue_url, String image_url, Keyword keyword) {
		this.title = title;
		this.issue_url = issue_url;
		this.image_url = image_url;
		this.keyword = keyword;
	}

	@Builder
	public static Issue create(Keyword keyword, String title, String issueUrl, String imageUrl) {
		Issue issue = new Issue(title, issueUrl, imageUrl, keyword);
		issue.addKeyword(keyword);
		return issue;
	}

	public void addKeyword(Keyword keyword) {
		this.keyword = keyword;
		keyword.getIssue().add(this);
	}

}
