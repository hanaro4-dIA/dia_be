package com.dia.dia_be.service.pb.intf;

import com.dia.dia_be.dto.pb.profileDTO.RequestProfileDTO;
import com.dia.dia_be.dto.pb.profileDTO.ResponseProfileDTO;

public interface ProfileServiceIntf {
	ResponseProfileDTO getProfile(Long pbId);

	RequestProfileDTO updateProfile(Long pbId, RequestProfileDTO profileDTO);
}
