package com.dia.dia_be.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;

import com.dia.dia_be.domain.Consulting;

public interface ConsultingRepository extends JpaRepository<Consulting, Long>, QuerydslPredicateExecutor<Consulting> {
	public List<Consulting> findConsultingsByApprove(boolean status);

	@Modifying
	@Query("UPDATE Consulting c SET c.title = :title, c.category.id = :categoryId WHERE c.id = :id")
	public void findConsultingByIdAndUpdateTitleAndCategory(
		@Param("id") Long consultingId,
		@Param("title") String consultingTitle,
		@Param("categoryId") Long categoryId);
}
