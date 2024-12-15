package com.dia.dia_be.repository;

import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.dia.dia_be.domain.Journal;
import com.dia.dia_be.domain.JournalProduct;
import com.dia.dia_be.domain.Product;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class ProductRepositoryTest {

	@Autowired
	private ProductRepository productRepository;

	@Autowired
	private JournalRepository journalRepository;

	private Product product;
	private Journal journal;

	@BeforeEach
	void setup() {
		journal = Journal.builder()
			.contents("Sample Journal")
			.complete(true)
			.build();
		journal = journalRepository.save(journal);

		product = Product.create("Sample Product", "http://example.com/product",
			"http://example.com/product_image.jpg");
		product = productRepository.save(product);

		JournalProduct journalProduct = JournalProduct.create(product, journal);
	}

	@Test
	void createProduct() {
		Product savedProduct = productRepository.save(product);

		Assertions.assertThat(savedProduct.getId()).isNotNull();
		Assertions.assertThat(savedProduct.getName()).isEqualTo("Sample Product");
		Assertions.assertThat(savedProduct.getProduct_url()).isEqualTo("http://example.com/product");
		Assertions.assertThat(savedProduct.getImage_url()).isEqualTo("http://example.com/product_image.jpg");
	}

	@Test
	void deleteProduct() {
		Product savedProduct = productRepository.save(product);

		productRepository.delete(savedProduct);
		List<Product> products = productRepository.findAll();

		Assertions.assertThat(products).doesNotContain(savedProduct);
	}
}
