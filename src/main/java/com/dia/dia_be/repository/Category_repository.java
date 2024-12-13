package com.dia.dia_be.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import com.dia.dia_be.domain.Category;

public interface Category_repository extends JpaRepository<Category, Long>, QuerydslPredicateExecutor<Category> {
}
