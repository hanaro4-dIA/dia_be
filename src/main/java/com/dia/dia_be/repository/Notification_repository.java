package com.dia.dia_be.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import com.dia.dia_be.domain.Notification;

public interface Notification_repository extends JpaRepository<Notification, Long>,
	QuerydslPredicateExecutor<Notification> {
}
