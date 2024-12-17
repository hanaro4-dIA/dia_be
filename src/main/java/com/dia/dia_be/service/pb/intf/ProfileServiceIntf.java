package com.dia.dia_be.service.pb.intf;

import com.dia.dia_be.dto.pb.profileDTO.ResponseEditProfileDTO;

import java.util.List;

public interface ProfileServiceIntf {
	ResponseEditProfileDTO updateProfile(Long pbId, String introduce, String imgUrl, List<String> hashTags);
}
