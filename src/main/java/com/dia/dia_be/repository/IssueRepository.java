package com.dia.dia_be.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;

import com.dia.dia_be.domain.Issue;

public interface IssueRepository extends JpaRepository<Issue, Long>, QuerydslPredicateExecutor<Issue> {
	@Query(value = """
		select distinct i.*
		  from journal_keyword jk
		  left outer
		  join keyword k
		    on jk.keyword_id = k.id
		  left outer
		  join issue i
		    on k.id = i.keyword_id
		 where jk.customer_id = :customerId
		   and i.id is not null
		 order by i.id desc
		 limit 4
		""", nativeQuery = true)
	List<Issue> findRecommendedIssues(@Param("customerId") Long customerId);

	boolean existsByTitle(String title);
}
