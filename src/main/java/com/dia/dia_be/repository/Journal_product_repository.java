package com.dia.dia_be.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import com.dia.dia_be.domain.Journal_product;

public interface Journal_product_repository extends JpaRepository<Journal_product, Long>,
	QuerydslPredicateExecutor<Journal_product> {
}
