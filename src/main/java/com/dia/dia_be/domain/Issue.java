package com.dia.dia_be.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

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

	@Column(nullable = false, columnDefinition = "VARCHAR(250) UNIQUE")
	private String issueUrl;

	@Column(nullable = false, columnDefinition = "VARCHAR(250)")
	private String imageUrl;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "keyword_id", nullable = false)
	private Keyword keyword;

	private Issue(String title, String issueUrl, String imageUrl, Keyword keyword) {
		this.title = title;
		this.issueUrl = issueUrl;
		this.imageUrl = imageUrl;
		this.keyword = keyword;
	}

	@Builder
	public static Issue create(Keyword keyword, String title, String issueUrl, String imageUrl) {
		Issue issue = new Issue(title, issueUrl, imageUrl, keyword);
		issue.addKeyword(keyword);
		return issue;
	}

	public Issue update(String title, String issueUrl, String imageUrl) {
		this.title = title;
		this.issueUrl = issueUrl;
		this.imageUrl = imageUrl;
		return this;
	}

	public void addKeyword(Keyword keyword) {
		this.keyword = keyword;
		keyword.getIssue().add(this);
	}

}
