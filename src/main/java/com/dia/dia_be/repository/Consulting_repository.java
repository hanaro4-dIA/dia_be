package com.dia.dia_be.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import com.dia.dia_be.domain.Consulting;

public interface Consulting_repository extends JpaRepository<Consulting, Long>, QuerydslPredicateExecutor<Consulting> {
}
