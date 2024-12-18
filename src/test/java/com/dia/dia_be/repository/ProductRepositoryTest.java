package com.dia.dia_be.repository;

import static org.assertj.core.api.AssertionsForClassTypes.*;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

import com.dia.dia_be.domain.Product;

import jakarta.transaction.Transactional;

@DataJpaTest
@Transactional
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class ProductRepositoryTest {

	@Autowired
	private ProductRepository productRepository;

	@Autowired
	private TestEntityManager entityManager;

	@Test
	public void testCreateProduct() {
		Product product = Product.create("Test Product", "http://test.com/product", "http://test.com/image");
		Product savedProduct = productRepository.save(product);

		assertThat(savedProduct).isNotNull();
		assertThat(savedProduct.getId()).isNotNull();
		assertThat(savedProduct.getName()).isEqualTo("Test Product");
		assertThat(savedProduct.getProduct_url()).isEqualTo("http://test.com/product");
		assertThat(savedProduct.getImage_url()).isEqualTo("http://test.com/image");
	}

	@Test
	public void getProductsTest(){
		String tag = "전세";
		List<Product> products = productRepository.findAllByName(tag);

		assertThat(products.size()).isGreaterThan(0);
	}
}
