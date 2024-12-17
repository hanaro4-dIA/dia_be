package com.dia.dia_be.service.vip.impl;

import java.time.LocalDate;
import java.time.LocalTime;

import org.springframework.stereotype.Service;

import com.dia.dia_be.domain.Consulting;
import com.dia.dia_be.dto.vip.RequestReservePostDTO;
import com.dia.dia_be.exception.CommonErrorCode;
import com.dia.dia_be.exception.GlobalException;
import com.dia.dia_be.repository.CategoryRepository;
import com.dia.dia_be.repository.ConsultingRepository;
import com.dia.dia_be.repository.CustomerRepository;
import com.dia.dia_be.service.vip.intf.ReserveService;

@Service
public class VipReserveServiceImpl implements ReserveService {

	private final ConsultingRepository consultingRepository;
	private final CategoryRepository categoryRepository;
	private final CustomerRepository customerRepository;

	public VipReserveServiceImpl(ConsultingRepository consultingRepository, CategoryRepository categoryRepository,
		CustomerRepository customerRepository) {
		this.consultingRepository = consultingRepository;
		this.categoryRepository = categoryRepository;
		this.customerRepository = customerRepository;
	}

	@Override
	public Long addReserve(Long customerId, RequestReservePostDTO requestReservePostDTO) {
		Consulting consulting = Consulting.create(
			categoryRepository.findById(requestReservePostDTO.getCategoryId()).orElseThrow(() -> new GlobalException(
				CommonErrorCode.BAD_REQUEST))
			,
			customerRepository.findById(customerId).orElseThrow(() -> new GlobalException(CommonErrorCode.BAD_REQUEST))
			, requestReservePostDTO.getTitle()
			, requestReservePostDTO.getDate()
			, requestReservePostDTO.getTime()
			, LocalDate.now()
			, LocalTime.now()
			, requestReservePostDTO.getContent()
			, false
		);
		Consulting consultingToAdd = consultingRepository.save(consulting);
		return consultingToAdd.getId();
	}
}
