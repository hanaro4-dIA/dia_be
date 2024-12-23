package com.dia.dia_be.service.vip.impl;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Comparator;
import java.util.List;
import java.util.stream.StreamSupport;

import org.springframework.stereotype.Service;

import com.dia.dia_be.domain.Consulting;
import com.dia.dia_be.domain.Customer;
import com.dia.dia_be.domain.QConsulting;
import com.dia.dia_be.dto.vip.reserveDTO.RequestReserveDTO;
import com.dia.dia_be.dto.vip.reserveDTO.ResponseReserveDTO;
import com.dia.dia_be.dto.vip.reserveDTO.ResponseReserveInfoDTO;
import com.dia.dia_be.exception.CommonErrorCode;
import com.dia.dia_be.exception.GlobalException;
import com.dia.dia_be.exception.VipErrorCode;
import com.dia.dia_be.repository.CategoryRepository;
import com.dia.dia_be.repository.ConsultingRepository;
import com.dia.dia_be.repository.CustomerRepository;
import com.dia.dia_be.service.vip.intf.VipReserveService;

@Service

public class VipReserveServiceImpl implements VipReserveService {

	private final ConsultingRepository consultingRepository;
	private final CategoryRepository categoryRepository;
	private final CustomerRepository customerRepository;
	private final QConsulting qConsulting = QConsulting.consulting;

	public VipReserveServiceImpl(ConsultingRepository consultingRepository, CategoryRepository categoryRepository,
		CustomerRepository customerRepository) {
		this.consultingRepository = consultingRepository;
		this.categoryRepository = categoryRepository;
		this.customerRepository = customerRepository;
	}

	@Override
	public Long addReserve(Long customerId, RequestReserveDTO requestReserveDTO) {
		if (requestReserveDTO.getCategoryId().equals(1L)) {
			Iterable<Consulting> c = consultingRepository.findAll(
				qConsulting.customer.id.eq(customerId)
					.and(qConsulting.category.id.eq(1L))
					.and(qConsulting.approve.eq(false))
			);
			if (c.iterator().hasNext()) {
				throw (new GlobalException(VipErrorCode.FAST_RESERVE_DENIED));
			}
		}
		Consulting consulting = Consulting.create(
			categoryRepository.findById(requestReserveDTO.getCategoryId()).orElseThrow(() -> new GlobalException(
				CommonErrorCode.BAD_REQUEST))
			,
			customerRepository.findById(customerId).orElseThrow(() -> new GlobalException(CommonErrorCode.BAD_REQUEST))
			, requestReserveDTO.getTitle()
			, requestReserveDTO.getDate()
			, requestReserveDTO.getTime()
			, LocalDate.now()
			, LocalTime.now()
			, requestReserveDTO.getContent()
			, false
		);
		Consulting consultingToAdd = consultingRepository.save(consulting);
		return consultingToAdd.getId();
	}

	@Override
	public ResponseReserveInfoDTO getInfo(Long customerId) {
		Customer customer = customerRepository.findById(customerId)
			.orElseThrow(() -> new GlobalException(CommonErrorCode.BAD_REQUEST));
		String pbName = customer.getPb().getName();
		String vipName = customer.getName();

		return new ResponseReserveInfoDTO(pbName, vipName);
	}

	@Override
	public List<ResponseReserveDTO> getReserves(Long customerId) {
		List<Consulting> reserves = StreamSupport.stream(consultingRepository.findAll(
					qConsulting.customer.id.eq(customerId).and(qConsulting.hopeDate.after(LocalDate.now().minusDays(1))))
				.spliterator(),
			false).toList();
		return reserves.stream()
			.map(ResponseReserveDTO::from)
			.sorted(Comparator.comparing(ResponseReserveDTO::getDate).thenComparing(ResponseReserveDTO::getTime))
			.toList();
	}

	@Override
	public ResponseReserveDTO getReserveByConsultingId(Long consultingId) {
		Consulting consulting = consultingRepository.findOne(
			qConsulting.id.eq(consultingId)
		).orElseThrow(() -> new GlobalException(CommonErrorCode.BAD_REQUEST));
		return ResponseReserveDTO.from(consulting);
	}

	@Override
	public Long deleteReserve(Long consultingId) {
		Consulting consultingToDelete = consultingRepository.findById(consultingId)
			.orElseThrow(() -> new GlobalException(CommonErrorCode.BAD_REQUEST));
		consultingRepository.delete(consultingToDelete);
		return consultingToDelete.getId();
	}
}
