package com.dia.dia_be.domain;

import jakarta.persistence.*;
import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class JournalKeyword {

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
	public static JournalKeyword create(Keyword keyword, Journal journal, Customer customer) {
		JournalKeyword journalKeyword = new JournalKeyword();
		journalKeyword.addKeyword(keyword);
		journalKeyword.addJournal(journal);
		journalKeyword.addCustomer(customer);
		return journalKeyword;
	}

	public void addKeyword(Keyword keyword) {
		this.keyword = keyword;
		keyword.getJournalKeyword().add(this);
	}

	public void addJournal(Journal journal) {
		this.journal = journal;
		journal.getJournalKeyword().add(this);
	}

	public void addCustomer(Customer customer) {
		this.customer = customer;
		customer.getJournalKeyword().add(this);
	}
}
