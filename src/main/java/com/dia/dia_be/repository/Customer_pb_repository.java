package com.dia.dia_be.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.dia.dia_be.domain.Customer_pb;

public interface Customer_pb_repository extends JpaRepository<Customer_pb, Long> {

	@Query(value = "SELECT CP.* FROM CUSTOMER_PB AS CP LEFT OUTER JOIN CUSTOMER AS C ON CP.CUSTOMER_ID = C.ID WHERE C.ID = :customerId AND CP.DATE = ANY(SELECT MAX(CP2.DATE) FROM CUSTOMER_PB CP2 WHERE CP2.CUSTOMER_ID = C.ID)",nativeQuery = true)
	Customer_pb findByCustomerId(@Param("customerId") Long customerId);
}
