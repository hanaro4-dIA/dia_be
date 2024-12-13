package com.dia.dia_be.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import com.dia.dia_be.domain.Journal;

public interface JournalRepository extends JpaRepository<Journal, Long>, QuerydslPredicateExecutor<Journal> {
}
