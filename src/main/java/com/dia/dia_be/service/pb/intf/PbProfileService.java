package com.dia.dia_be.service.pb.intf;

import java.util.List;

import com.dia.dia_be.domain.Pb;
import com.dia.dia_be.dto.pb.availabilityDTO.RequestAvailabilityDTO;
import com.dia.dia_be.dto.pb.profileDTO.ResponseEditProfileDTO;
import com.dia.dia_be.dto.pb.profileDTO.ResponseProfileDTO;

public interface PbProfileService {
	ResponseProfileDTO getProfile(Long pbId);

	ResponseEditProfileDTO updateProfile(Long pbId, String introduce, String imgUrl, List<String> hashTags);

	Pb login(String id, String pw);

	RequestAvailabilityDTO updateAvailability(RequestAvailabilityDTO requestAvailabilityDTO);
}
