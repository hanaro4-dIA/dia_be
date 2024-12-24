package com.dia.dia_be.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.dia.dia_be.domain.Journal;
import com.dia.dia_be.dto.pb.journalDTO.ResponseTemporarySavedJournalDTO;

public interface JournalRepository extends JpaRepository<Journal, Long>, QuerydslPredicateExecutor<Journal> {
	@Modifying
	@Query("UPDATE Journal j SET j.complete = true WHERE j.id = :id")
	public void updateCompleteStatusById(@Param("id") Long id);

	@Modifying
	@Query("UPDATE Journal j SET j.contents = :contents WHERE j.id = :id")
	public void updateContentsById(@Param("id") Long id, @Param("contents") String contents);

	@Query("SELECT j FROM Journal j WHERE j.id = :id AND j.complete = :status")
	public Optional<Journal> findByIdAndCompleteFalse(Long id, boolean status);

	@Modifying
	@Query("DELETE Script s WHERE s.id = :scriptId AND s.journal.id = :journalId AND s.scriptSequence = :scriptSequence")
	public void deleteScriptById(@Param("journalId") Long journalId, @Param("scriptId") Long scriptId, @Param("scriptSequence") Long scriptSequence);
}
