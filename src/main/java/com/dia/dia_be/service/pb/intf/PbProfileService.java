package com.dia.dia_be.service.pb.intf;

import com.dia.dia_be.domain.Pb;
import com.dia.dia_be.dto.pb.profileDTO.ResponseEditProfileDTO;
import com.dia.dia_be.dto.pb.profileDTO.ResponseProfileDTO;

import java.util.List;

public interface PbProfileService {
	ResponseProfileDTO getProfile(Long pbId);
	ResponseEditProfileDTO updateProfile(Long pbId, String introduce, String imgUrl, List<String> hashTags);

	Pb login(String id, String pw);
}
