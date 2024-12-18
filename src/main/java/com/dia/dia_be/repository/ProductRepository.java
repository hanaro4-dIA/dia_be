package com.dia.dia_be.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import com.dia.dia_be.domain.Product;

public interface ProductRepository extends JpaRepository<Product, Long>, QuerydslPredicateExecutor<Product> {
	@Query("SELECT p FROM Product p WHERE p.name LIKE %:tag%")
	List<Product> findAllByName(String tag);
}
