package com.dia.dia_be.controller.pb;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dia.dia_be.dto.pb.profileDTO.RequestProfileDTO;
import com.dia.dia_be.dto.pb.profileDTO.ResponseProfileDTO;
import com.dia.dia_be.service.pb.intf.ProfileServiceIntf;

@RequestMapping("pb")
@RestController
public class ProfileController {

	private final ProfileServiceIntf profileService;

	public ProfileController(ProfileServiceIntf profileService) {
		this.profileService = profileService;
	}

	@GetMapping("/profile/{pbId}")
	public ResponseProfileDTO getProfile(@PathVariable("pbId") Long pbId) {
		return profileService.getProfile(pbId);
	}

	@PutMapping("/profile/{pbId}")
	public RequestProfileDTO updateProfile(@PathVariable("pbId") Long pbId,
		@RequestBody RequestProfileDTO requestProfileDTO) {
		return profileService.updateProfile(pbId, requestProfileDTO);
	}
}
