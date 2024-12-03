package com.dia.dia_be.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dia.dia_be.domain.Customer;

public interface Customer_repository extends JpaRepository<Customer,Long> {
}
