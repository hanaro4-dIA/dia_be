package com.dia.dia_be.domain;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Keyword {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(columnDefinition = "INT UNSIGNED")
	private Long id;

	@Column(nullable = false, columnDefinition = "VARCHAR(20)")
	private String title;

	@Column(nullable = false, columnDefinition = "VARCHAR(300)")
	private String content;

	@OneToMany(mappedBy = "keyword", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Issue> issue = new ArrayList<>();

	@OneToMany(mappedBy = "keyword", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Journal_keyword> journal_keyword = new ArrayList<>();

	private Keyword(String title, String content) {
		this.title = title;
		this.content = content;
	}

	@Builder
	public Keyword create(String title, String content, String url) {
		return new Keyword(title, content);
	}


}
