package com.dia.dia_be.service.pb.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.dia.dia_be.domain.Consulting;
import com.dia.dia_be.dto.pb.ReservesDTO.ResponseReserveDTO;
import com.dia.dia_be.exception.GlobalException;
import com.dia.dia_be.exception.PbErrorCode;
import com.dia.dia_be.repository.ConsultingRepository;
import com.dia.dia_be.service.pb.intf.ReserveService;

@Service
public class ReserveServiceImpl implements ReserveService {

	private final ConsultingRepository consultingRepository;

	public ReserveServiceImpl(ConsultingRepository consultingRepository) {
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
