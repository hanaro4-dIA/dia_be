package com.dia.dia_be.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.dia.dia_be.domain.Customer;

public interface Customer_repository extends JpaRepository<Customer, Long> {
}
