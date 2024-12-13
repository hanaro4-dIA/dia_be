package com.dia.dia_be.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import com.dia.dia_be.domain.Hashtag;

public interface Pb_hashtag_repository extends JpaRepository<Hashtag, Long>, QuerydslPredicateExecutor<Hashtag> {
}
