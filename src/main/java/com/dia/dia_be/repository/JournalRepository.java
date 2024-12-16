package com.dia.dia_be.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dia.dia_be.domain.Journal;

public interface JournalRepository extends JpaRepository<Journal, Long> {
}
