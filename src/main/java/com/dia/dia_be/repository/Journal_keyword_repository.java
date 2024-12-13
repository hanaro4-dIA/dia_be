package com.dia.dia_be.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import com.dia.dia_be.domain.Journal_keyword;

public interface Journal_keyword_repository extends JpaRepository<Journal_keyword, Long>,
	QuerydslPredicateExecutor<Journal_keyword> {
}
