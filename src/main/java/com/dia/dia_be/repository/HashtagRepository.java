package com.dia.dia_be.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import com.dia.dia_be.domain.Hashtag;
import com.dia.dia_be.domain.Pb;

public interface HashtagRepository extends JpaRepository<Hashtag, Long>, QuerydslPredicateExecutor<Hashtag> {
	List<Hashtag> findAllByPb(Pb pb);
}
