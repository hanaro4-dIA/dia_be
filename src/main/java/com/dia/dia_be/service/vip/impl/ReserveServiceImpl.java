package com.dia.dia_be.service.vip.impl;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.dia.dia_be.domain.Category;
import com.dia.dia_be.domain.Consulting;
import com.dia.dia_be.dto.vip.RequestReservePostDTO;
import com.dia.dia_be.repository.CategoryRepository;
import com.dia.dia_be.repository.ConsultingRepository;
import com.dia.dia_be.repository.CustomerRepository;
import com.dia.dia_be.service.vip.intf.ReserveService;

@Service
public class ReserveServiceImpl implements ReserveService {

	private final ConsultingRepository consultingRepository;
	private final CategoryRepository categoryRepository;
	private final CustomerRepository customerRepository;

	public ReserveServiceImpl(ConsultingRepository consultingRepository, CategoryRepository categoryRepository,
		CustomerRepository customerRepository) {
		this.consultingRepository = consultingRepository;
		this.categoryRepository = categoryRepository;
		this.customerRepository = customerRepository;
	}

	@Override
	public Long addReserve(Long customerId, RequestReservePostDTO requestReservePostDTO) {
		Optional<Category> category = categoryRepository.findById(requestReservePostDTO.getCategoryId());
		System.out.println("!!!!!!!!!!!!!!!!");
		if (category.isPresent()) {
			System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!" + category.get().getName());
		}
		Consulting consulting = Consulting.create(
			categoryRepository.findById(requestReservePostDTO.getCategoryId()).orElseThrow()
			, customerRepository.findById(customerId).orElseThrow()
			, requestReservePostDTO.getTitle()
			, requestReservePostDTO.getDate()
			, requestReservePostDTO.getTime()
			, LocalDate.now()
			, LocalTime.now()
			, requestReservePostDTO.getContent()
			, false
		);
		System.out.println("!!!!!!!!!!!!!!!!!" + consulting);
		Consulting consultingToAdd = consultingRepository.save(consulting);
		return consultingToAdd.getId();
	}
}
