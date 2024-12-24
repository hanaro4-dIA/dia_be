package com.dia.dia_be.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;

import com.dia.dia_be.domain.Product;

public interface ProductRepository extends JpaRepository<Product, Long>, QuerydslPredicateExecutor<Product> {
	@Query("SELECT p FROM Product p WHERE p.name LIKE %:tag%")
	List<Product> findAllByName(String tag);

	@Query(
		value = """
			SELECT DISTINCT P.*
			  FROM CONSULTING C
			  LEFT OUTER
			  JOIN JOURNAL J
			    ON C.ID = J.CONSULTING_ID
			  LEFT OUTER
			  JOIN JOURNAL_PRODUCT JP
			    ON J.ID = JP.JOURNAL_ID
			  LEFT OUTER
			  JOIN PRODUCT P
			    ON JP.PRODUCT_ID = P.ID
			 WHERE C.CUSTOMER_ID = :customerId
			   AND JP.PRODUCT_ID IS NOT NULL
			 ORDER BY P.ID DESC
			 LIMIT 4
			"""
		, nativeQuery = true)
	List<Product> findRecommendedProducts(@Param("customerId") Long customerId);
}
