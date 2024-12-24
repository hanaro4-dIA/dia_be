package com.dia.dia_be.domain;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Product {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(columnDefinition = "INT UNSIGNED")
	private Long id;

	@Column(nullable = false, columnDefinition = "VARCHAR(100)")
	private String name;

	@Column(nullable = false, columnDefinition = "VARCHAR(250)")
	private String product_url;

	@Column(nullable = false, columnDefinition = "VARCHAR(250)")
	private String image_url;

	@OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<JournalProduct> journalProduct = new ArrayList<>();

	private Product(String name, String product_url, String image_url) {
		this.name = name;
		this.product_url = product_url;
		this.image_url = image_url;
	}

	@Builder
	public static Product create(String name, String product_url, String image_url) {
		return new Product(name, product_url, image_url);
	}

	public Product update(String name, String product_url, String image_url) {
		this.name = name;
		this.product_url = product_url;
		this.image_url = image_url;
		return this;
	}
}
