package com.dia.dia_be.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import com.dia.dia_be.domain.Notification;

public interface NotificationRepository extends JpaRepository<Notification, Long>,
	QuerydslPredicateExecutor<Notification> {
	List<Notification> findByCustomerIdIn(List<Long> customerIds);
}
