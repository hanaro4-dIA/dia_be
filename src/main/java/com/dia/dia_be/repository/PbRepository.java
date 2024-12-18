package com.dia.dia_be.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import com.dia.dia_be.domain.Pb;

public interface PbRepository extends JpaRepository<Pb, Long>, QuerydslPredicateExecutor<Pb> {
	Optional<Pb> findByLoginIdAndPassword(String loginId, String password);
}
