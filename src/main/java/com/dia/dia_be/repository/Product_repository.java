package com.dia.dia_be.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dia.dia_be.domain.Product;

public interface Product_repository extends JpaRepository<Product, Long> {
}
