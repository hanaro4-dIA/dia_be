package com.dia.dia_be.domain;

import java.time.LocalDateTime;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Reserve {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(columnDefinition = "INT UNSIGNED")
	private Long id;

	@Column(nullable = false, columnDefinition = "VARCHAR(500)")
	private String content;

	@Column(nullable = false, columnDefinition = "TINYINT(1)")
	private boolean approve;

	@Column(nullable = false, columnDefinition = "TIMESTAMP")
	private LocalDateTime date;

	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "consulting_id")
	private Consulting consulting;

	private Reserve(String content, boolean approve, LocalDateTime date) {
		this.content = content;
		this.approve = approve;
		this.date = date;
	}

	@Builder
	public static Reserve create(String content, String file_url, boolean approve, LocalDateTime date) {
		return new Reserve(content, approve, date);
	}

	protected void setConsulting(Consulting consulting) {
		this.consulting = consulting;
	}

}
