package com.dia.dia_be.controller.pb;

import static com.dia.dia_be.exception.CommonErrorCode.*;

import java.io.IOException;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.dia.dia_be.dto.pb.availabilityDTO.RequestAvailabilityDTO;
import com.dia.dia_be.dto.pb.profileDTO.ResponseEditProfileDTO;
import com.dia.dia_be.dto.pb.profileDTO.ResponseProfileDTO;
import com.dia.dia_be.exception.GlobalException;
import com.dia.dia_be.global.S3.S3Service;
import com.dia.dia_be.service.pb.intf.PbProfileService;
import com.dia.dia_be.websocket.PbAvailabilityHandler;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import com.dia.dia_be.service.pb.intf.PbProfileService;
import com.dia.dia_be.websocket.PbAvailabilityHandler;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RequestMapping("/pb")
@RestController
public class PbProfileController {

	private final PbProfileService pbProfileService;
	private final S3Service s3Service;
	private final PbAvailabilityHandler pbAvailabilityHandler;

	public PbProfileController(PbProfileService profileService, S3Service s3Service,
		PbAvailabilityHandler pbAvailabilityHandler) {
		this.pbProfileService = profileService;
		this.s3Service = s3Service;
		this.pbAvailabilityHandler = pbAvailabilityHandler;
	}

	@Operation(summary = "PB 프로필 조회", description = "PB 프로필을 조회합니다.")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "요청에 성공하였습니다.",
			content = @Content(mediaType = "application/json")),
		@ApiResponse(responseCode = "404", description = "PB 프로필을 찾을 수 없습니다.")
	})
	@GetMapping("/profile")
	public ResponseProfileDTO getProfile() {
		Long pbId = 1L;
		return pbProfileService.getProfile(pbId);
	}

	@Tag(name = "PB 프로필 업데이트", description = "PB의 프로필 업데이트 API")
	@Parameters({
		@Parameter(name = "introduce", description = "PB의 한줄 소개"),
		@Parameter(name = "file", description = "PB의 프로필 이미지, 변경 없을 시에는 전달X"),
		@Parameter(name = "hashTagList", description = "PB의 해시태그 리스트")
	})
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "요청에 성공하였습니다.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseProfileDTO.class))),
		@ApiResponse(responseCode = "500", description = "프로필 업데이트 실패 혹은 이미지 저장 실패")
	})
	@PutMapping("/profile")
	public ResponseEntity<ResponseEditProfileDTO> updateProfile(
		@RequestParam(required = false) MultipartFile file,
		@RequestParam(required = false) String introduce,
		@RequestParam List<String> hashtags) {

		Long pbId = 1L;

		String imgUrl = null;
		if (file != null) {
			try {
				imgUrl = s3Service.upload(file, "profile");
			} catch (IOException e) {
				throw new GlobalException(ILLEGAL_S3_REQUEST);
			}
		}

		return ResponseEntity.ok().body(pbProfileService.updateProfile(pbId, introduce, imgUrl, hashtags));
	}

	@Tag(name = "PB 상담 가능여부 업데이트", description = "PB의 상담 가능 업데이트 API")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "400", description = "요청 본문이 비어 있거나 잘못된 형식입니다.")
	})
	@PutMapping("/availability")
	public ResponseEntity<RequestAvailabilityDTO> updateAvailability(
		@Parameter(description = "상담 가능 여부를 업데이트하기 위한 요청 데이터", required = true,
			content = @Content(mediaType = "application/json",
				examples = @ExampleObject(name = "updateAvailabilityExample",
					value = "{ \"pbId\": 1, \"availability\": true }")))
		@RequestBody RequestAvailabilityDTO availabilityDTO) {
		pbAvailabilityHandler.notifyClients(availabilityDTO);
		return ResponseEntity.ok().body(pbProfileService.updateAvailability(availabilityDTO));

	}


}
