package com.dia.dia_be.service.pb.intf;

import com.dia.dia_be.dto.pb.profileDTO.ResponseProfileDTO;

public interface PbProfileService {
	ResponseProfileDTO getProfile(Long pbId);
}