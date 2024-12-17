package com.dia.dia_be.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import com.dia.dia_be.domain.Consulting;

public interface ConsultingRepository extends JpaRepository<Consulting, Long>, QuerydslPredicateExecutor<Consulting> {
	public List<Consulting> findConsultingsByApprove(boolean status);

	public List<Consulting> findByApproveTrueAndHopeDateAfter(LocalDate currentDate);
}
