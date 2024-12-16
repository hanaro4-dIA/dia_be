package com.dia.dia_be.controller.pb;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dia.dia_be.dto.pb.profileDTO.ResponseProfileDTO;
import com.dia.dia_be.service.pb.intf.ProfileServiceIntf;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RequestMapping("/pb")
@RestController
public class ProfileController {

	private final ProfileServiceIntf profileService;

	public ProfileController(ProfileServiceIntf profileService) {
		this.profileService = profileService;
	}

	@Operation(summary = "PB 프로필 조회", description = "PB 프로필을 조회합니다.")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "요청에 성공하였습니다.",
			content = @Content(mediaType = "application/json")),
		@ApiResponse(responseCode = "404", description = "PB 프로필을 찾을 수 없습니다.")
	})
	@GetMapping("/profile/{pbId}")
	public ResponseProfileDTO getProfile(@PathVariable Long pbId) {
		return profileService.getProfile(pbId);
	}

}
