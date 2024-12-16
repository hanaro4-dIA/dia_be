package com.dia.dia_be.service.pb.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.dia.dia_be.domain.Consulting;
import com.dia.dia_be.dto.pb.ReservesDTO.ResponseReserveDTO;
import com.dia.dia_be.repository.ConsultingRepository;
import com.dia.dia_be.service.pb.intf.PbReserveService;

import jakarta.transaction.Transactional;

@Service
@Transactional
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
	public void approveReserve(Long id) {
		Consulting consulting = consultingRepository.findById(id)
			.orElseThrow(() -> new RuntimeException("해당 ID와 일치하는 상담 요청이 존재하지 않습니다."));

		if (consulting.isApprove()) {
			throw new IllegalStateException("이미 승인된 요청입니다: " + consulting.getId());
		}

		consulting.setApprove(true);
		consultingRepository.save(consulting);
	}
}
