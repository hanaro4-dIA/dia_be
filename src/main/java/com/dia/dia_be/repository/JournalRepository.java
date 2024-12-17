package com.dia.dia_be.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;

import com.dia.dia_be.domain.Journal;

public interface JournalRepository extends JpaRepository<Journal, Long>, QuerydslPredicateExecutor<Journal> {
	@Modifying
	@Query("UPDATE Journal j SET j.complete = 1 WHERE j.id = :id")
	public void findJournalByConsultingIdAndMakeCompleteTrue(@Param("id") Long id);

	@Modifying
	@Query("UPDATE Journal j SET j.contents = :contents WHERE j.id = :id")
	public void findJournalByConsultingIdAndUpdateContents(@Param("id") Long id, @Param("contents") String contents);

}
