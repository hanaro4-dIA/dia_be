package com.dia.dia_be.domain;

import jakarta.persistence.*;
import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Journal_keyword {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(columnDefinition = "INT UNSIGNED")
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "keyword_id", nullable = false)
	private Keyword keyword;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "journal_id", nullable = false)
	private Journal journal;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "customer_id", nullable = false)
	private Customer customer;

	@Builder
	public static Journal_keyword create(Keyword keyword, Journal journal, Customer customer) {
		Journal_keyword journalKeyword = new Journal_keyword();
		journalKeyword.addKeyword(keyword);
		journalKeyword.addJournal(journal);
		journalKeyword.addCustomer(customer);
		return journalKeyword;
	}

	public void addKeyword(Keyword keyword) {
		this.keyword = keyword;
		keyword.getJournal_keyword().add(this);
	}

	public void addJournal(Journal journal) {
		this.journal = journal;
		journal.getJournal_keyword().add(this);
	}

	public void addCustomer(Customer customer) {
		this.customer = customer;
		customer.getJournal_keyword().add(this);
	}
}
