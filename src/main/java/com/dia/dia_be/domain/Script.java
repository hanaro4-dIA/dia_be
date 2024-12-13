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
import lombok.Setter;

@Getter
@Setter(AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Script {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(columnDefinition = "INT UNSIGNED")
	private Long id;

	@Column(nullable = false, columnDefinition = "INT UNSIGNED")
	private int scriptSequence;

	@Column(nullable = false, columnDefinition = "TEXT")
	private String speaker;

	@Column(nullable = false, columnDefinition = "LONGTEXT")
	private String content;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "journal_id")
	private Journal journal;

	private Script(int scriptSequence, String speaker, String content) {
		this.scriptSequence = scriptSequence;
		this.speaker = speaker;
		this.content = content;
	}

	@Builder
	public static Script create(Journal journal, int script_sequence, String speaker, String content) {
		Script script = new Script(script_sequence, speaker, content);
		script.addJournal(journal);
		return script;
	}

	public Script update(int scriptSequence, String speaker, String content){
		this.scriptSequence = scriptSequence;
		this.speaker = speaker;
		this.content = content;
		return this;
	}

	private void addJournal(Journal journal) {
		this.journal = journal;
		journal.getScript().add(this);
	}
}
