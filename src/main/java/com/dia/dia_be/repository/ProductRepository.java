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
			 select distinct p.*
			   from consulting c
			   left outer
			   join journal j
			     on c.id = j.consulting_id
			   left outer
			   join journal_product jp
			     on j.id = jp.journal_id
			   left outer
			   join product p
			     on jp.product_id = p.id
			  where c.customer_id = :customerId
			    and jp.product_id is not null
			  order by p.id desc
			  limit 4
			"""
		, nativeQuery = true)
	List<Product> findRecommendedProducts(@Param("customerId") Long customerId);
}
