package com.dia.dia_be.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import com.dia.dia_be.domain.JournalKeyword;

public interface JournalKeywordRepository extends JpaRepository<JournalKeyword, Long>,
	QuerydslPredicateExecutor<JournalKeyword> {

	List<JournalKeyword> findALLByJournalId(Long journalId);
}
