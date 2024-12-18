package com.dia.dia_be.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;

import com.dia.dia_be.domain.Issue;

public interface IssueRepository extends JpaRepository<Issue, Long>, QuerydslPredicateExecutor<Issue> {
	@Query(value = """
		SELECT DISTINCT I.*
		  FROM JOURNAL_KEYWORD JK
		  LEFT OUTER
		  JOIN KEYWORD K
		    ON JK.KEYWORD_ID = K.ID
		  LEFT OUTER
		  JOIN ISSUE I
		    ON K.ID = I.KEYWORD_ID
		 WHERE JK.CUSTOMER_ID = :customerId
		   AND I.ID IS NOT NULL
		 ORDER BY I.ID DESC
		 LIMIT 4
		""", nativeQuery = true)
	List<Issue> findRecommendedIssues(@Param("customerId") Long customerId);
}
