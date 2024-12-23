package com.dia.dia_be.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.dia.dia_be.domain.Consulting;

public interface ConsultingRepository extends JpaRepository<Consulting, Long>, QuerydslPredicateExecutor<Consulting> {
	public List<Consulting> findConsultingsByApprove(boolean status);

	@Modifying
	@Query("UPDATE Consulting c SET c.title = :title, c.category.id = :categoryId WHERE c.id = :id")
	public void updateTitleAndCategory(
		@Param("id") Long consultingId,
		@Param("title") String consultingTitle,
		@Param("categoryId") Long categoryId);

	List<Consulting> findByHopeDateAndApproveTrueAndCustomer_Pb_Id(LocalDate date, Long pbId);

	public List<Consulting> findByApproveAndJournal_Complete(boolean approve, boolean complete);

}
