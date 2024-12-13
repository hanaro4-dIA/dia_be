package com.dia.dia_be.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import com.dia.dia_be.domain.Keyword;

public interface Keyword_repository extends JpaRepository<Keyword, Long>, QuerydslPredicateExecutor<Keyword> {
}
