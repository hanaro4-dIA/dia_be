package com.dia.dia_be.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import com.dia.dia_be.domain.Customer;

public interface Customer_repository extends JpaRepository<Customer, Long>, QuerydslPredicateExecutor<Customer> {
}
