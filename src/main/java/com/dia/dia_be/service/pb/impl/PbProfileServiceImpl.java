package com.dia.dia_be.service.pb.impl;

import org.springframework.stereotype.Service;

import com.dia.dia_be.domain.Pb;
import com.dia.dia_be.dto.pb.profileDTO.ResponseProfileDTO;
import com.dia.dia_be.exception.GlobalException;
import com.dia.dia_be.exception.PbErrorCode;
import com.dia.dia_be.repository.PbRepository;
import com.dia.dia_be.service.pb.intf.PbProfileService;

@Service
public class PbProfileServiceImpl implements PbProfileService {
	private final PbRepository pbRepository;

	public PbProfileServiceImpl(PbRepository pbRepository) {
		this.pbRepository = pbRepository;
	}

	@Override
	public ResponseProfileDTO getProfile(Long pbId) {
		Pb pb = pbRepository.findById(pbId)
			.orElseThrow(() -> new GlobalException(PbErrorCode.PROFILE_NOT_FOUND));

		return ResponseProfileDTO.from(pb);
	}
}
