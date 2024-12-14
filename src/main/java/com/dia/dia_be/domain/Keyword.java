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

	@Column(nullable = false, columnDefinition = "VARCHAR(100)")
	private String title;

	@Column(nullable = false, columnDefinition = "TEXT")
	private String content;

	@OneToMany(mappedBy = "keyword", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Issue> issue = new ArrayList<>();

	@OneToMany(mappedBy = "keyword", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<JournalKeyword> journalKeyword = new ArrayList<>();

	private Keyword(String title, String content) {
		this.title = title;
		this.content = content;
	}

	@Builder
	public Keyword create(String title, String content) {
		return new Keyword(title, content);
	}

	public Keyword update(String title, String content) {
		this.title = title;
		this.content = content;
		return this;
	}
}
