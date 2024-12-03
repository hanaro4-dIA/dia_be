package com.dia.dia_be.service.vip.impl;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import com.dia.dia_be.domain.Consulting;
import com.dia.dia_be.domain.Customer;
import com.dia.dia_be.domain.Customer_pb;
import com.dia.dia_be.domain.Reserve;
import com.dia.dia_be.dto.vip.Reserve_dto;
import com.dia.dia_be.dto.vip.Reserve_info_dto;
import com.dia.dia_be.dto.vip.Reserve_post_dto;
import com.dia.dia_be.dto.vip.Reserve_simple_dto;
import com.dia.dia_be.repository.Category_repository;
import com.dia.dia_be.repository.Consulting_repository;
import com.dia.dia_be.repository.Customer_pb_repository;
import com.dia.dia_be.repository.Customer_repository;
import com.dia.dia_be.repository.Pb_repository;
import com.dia.dia_be.repository.Reserve_repository;
import com.dia.dia_be.service.vip.intf.reserve_service;
import com.dia.dia_be.util.DateTime;

public class reserve_service_impl implements reserve_service {
	private final Category_repository category_repository;
	private final Customer_repository customer_repository;
	private final Pb_repository pb_repository;
	private final Customer_pb_repository customer_pb_repository;
	private final Consulting_repository consulting_repository;
	private final Reserve_repository reserve_repository;

	public reserve_service_impl(Category_repository category_repository, Customer_repository customer_repository,
		Pb_repository pb_repository, Customer_pb_repository customer_pb_repository,
		Consulting_repository consulting_repository, Reserve_repository reserve_repository) {
		this.category_repository = category_repository;
		this.customer_repository = customer_repository;
		this.pb_repository = pb_repository;
		this.customer_pb_repository = customer_pb_repository;
		this.consulting_repository = consulting_repository;
		this.reserve_repository = reserve_repository;
	}

	@Override
	public List<Reserve_simple_dto> getReserves(Long vipId) {
		return List.of();
	}

	@Override
	public Reserve_info_dto getReserveInfo(Long vipId) {
		return null;
	}

	@Override
	public void postReserve(Long vipId, Reserve_post_dto dto) {
		Customer_pb customer_pb = customer_pb_repository.findByCustomerId(vipId);
		Consulting consulting = new Consulting(dto.getTitle(), DateTime.stringToLocalDate(dto.getDate()),DateTime.stringToLocalTime(dto.getTime()));
		consulting_repository.save(consulting);
		Reserve reserve = new Reserve(dto.getContent(),false, LocalDateTime.now());
		reserve_repository.save(reserve);
	}

	@Override
	public Reserve_dto getReserveConfirm(Long reserveId) {
		return null;
	}
}
