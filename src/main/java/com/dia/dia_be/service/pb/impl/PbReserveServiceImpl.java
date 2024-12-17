package com.dia.dia_be.service.pb.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.dia.dia_be.domain.Consulting;
import com.dia.dia_be.dto.pb.reservesDTO.ResponseReserveDTO;
import com.dia.dia_be.exception.GlobalException;
import com.dia.dia_be.exception.PbErrorCode;
import com.dia.dia_be.repository.ConsultingRepository;
import com.dia.dia_be.service.pb.intf.PbReserveService;

@Service

public class PbReserveServiceImpl implements PbReserveService {

	private final ConsultingRepository consultingRepository;

	public PbReserveServiceImpl(ConsultingRepository consultingRepository) {
		this.consultingRepository = consultingRepository;
	}

	@Override
	public List<ResponseReserveDTO> getApprovedReserves(boolean status) {
		return consultingRepository.findConsultingsByApprove(status)
			.stream()
			.map(ResponseReserveDTO::from)
			.toList();

	}

	@Override
	public String getContent(Long id) {
		Consulting consulting = consultingRepository.findById(id)
			.orElseThrow(() -> new GlobalException(PbErrorCode.RESERVE_NOT_FOUND));

		return consulting.getContent();
	}
}
