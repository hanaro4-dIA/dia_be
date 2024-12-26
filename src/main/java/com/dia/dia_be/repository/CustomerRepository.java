package com.dia.dia_be.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import com.dia.dia_be.domain.Customer;
import com.dia.dia_be.domain.Pb;

import lombok.NonNull;

public interface CustomerRepository extends JpaRepository<Customer, Long>, QuerydslPredicateExecutor<Customer> {

	@NonNull
	List<Customer> findAll();

	List<Customer> findByPb_idAndNameContaining(Long id, String name);

	List<Customer> findByPbId(Long pbId);

	Optional<Customer> findCustomerByEmailAndPassword(String email, String password);
}
