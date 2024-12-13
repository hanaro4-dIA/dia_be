package com.dia.dia_be.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import com.dia.dia_be.domain.Product;

public interface Product_repository extends JpaRepository<Product, Long>, QuerydslPredicateExecutor<Product> {
}
