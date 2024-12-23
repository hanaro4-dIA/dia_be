package com.dia.dia_be.service.pb.impl;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;

import org.springframework.stereotype.Service;

import com.dia.dia_be.domain.Consulting;
import com.dia.dia_be.dto.pb.reservesDTO.ResponseReserveByDateDTO;
import com.dia.dia_be.dto.pb.reservesDTO.ResponseReserveDTO;
import com.dia.dia_be.exception.GlobalException;
import com.dia.dia_be.exception.PbErrorCode;
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
	public List<ResponseReserveDTO> getNotCompletedReserves() {
		return consultingRepository.findByApproveAndJournal_Complete(true,false)
			.stream()
			.map(ResponseReserveDTO::from)
			.sorted(
				Comparator.comparing(ResponseReserveDTO::getHopeDate).thenComparing(ResponseReserveDTO::getHopeTime))
			.toList();
	}

	@Override
	public void approveReserve(Long id) {
		// 해당 ID와 일치하는 상담 요청이 존재하지 않음
		Consulting consulting = consultingRepository.findById(id)
			.orElseThrow(() -> new RuntimeException(PbErrorCode.RESERVE_NOT_FOUND.getMessage()));

		// 이미 승인된 요청
		if (consulting.isApprove()) {
			throw new IllegalStateException(PbErrorCode.INVALID_RESERVE_APPROVAL.getMessage());
		}

		consulting.setApprove(true);
		consultingRepository.save(consulting);
	}

	@Override
	public String getContent(Long id) {
		Consulting consulting = consultingRepository.findById(id)
			.orElseThrow(() -> new GlobalException(PbErrorCode.RESERVE_NOT_FOUND));

		return consulting.getContent();
	}

	@Override
	public List<ResponseReserveByDateDTO> getReservesByDate(LocalDate date, Long pbId) {
		List<Consulting> consultings = consultingRepository.findByHopeDateAndApproveTrueAndCustomer_Pb_Id(date,
			pbId);
		if (consultings.isEmpty()) {
			throw new GlobalException(PbErrorCode.RESERVE_NOT_FOUND);
		}

		return ResponseReserveByDateDTO.fromList(consultings);
	}
}
