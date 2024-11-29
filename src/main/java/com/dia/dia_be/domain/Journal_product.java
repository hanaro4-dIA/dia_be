package com.dia.dia_be.domain;

import jakarta.persistence.*;
import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Journal_product {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(columnDefinition = "INT UNSIGNED")
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "product_id", nullable = false)
	private Product product;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "journal_id", nullable = false)
	private Journal journal;

	@Builder
	public static Journal_product create(Product product, Journal journal) {
		Journal_product journalProduct = new Journal_product();
		journalProduct.addProduct(product);
		journalProduct.addJournal(journal);
		return journalProduct;
	}

	public void addProduct(Product product) {
		this.product = product;
		product.getJournal_product().add(this);
	}

	public void addJournal(Journal journal) {
		this.journal = journal;
		journal.getJournal_product().add(this);
	}
}
