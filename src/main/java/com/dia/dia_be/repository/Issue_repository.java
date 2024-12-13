package com.dia.dia_be.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import com.dia.dia_be.domain.Issue;

public interface Issue_repository extends JpaRepository<Issue, Long>, QuerydslPredicateExecutor<Issue> {
}
