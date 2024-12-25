package com.dia.dia_be.controller.pb;

import static com.dia.dia_be.exception.CommonErrorCode.*;

import java.io.IOException;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.dia.dia_be.domain.PbSessionConst;
import com.dia.dia_be.dto.pb.availabilityDTO.RequestAvailabilityDTO;
import com.dia.dia_be.dto.pb.loginDTO.LoginDTO;
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
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@RequestMapping("/pb")
@RestController
@Tag(name = "PB - 프로필", description = "프로필 처리 관련 API")
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
	public ResponseEntity<ResponseProfileDTO> getProfile(HttpServletRequest request) {
		// 세션 확인 코드 추가
		HttpSession session = request.getSession(false);
		if (session == null) { // 세션이 없으면 홈으로 이동
			return new ResponseEntity<>(null, HttpStatus.FOUND);
		}

		LoginDTO loginDTO = (LoginDTO)session.getAttribute(PbSessionConst.LOGIN_PB);
		if (loginDTO == null) { // 세션에 회원 데이터가 없으면 홈으로 이동
			return new ResponseEntity<>(null, HttpStatus.FOUND);
		}
		ResponseProfileDTO profile = pbProfileService.getProfile(loginDTO.getPbId());
		return new ResponseEntity<>(profile, HttpStatus.OK);
	}

	@Parameters({
		@Parameter(name = "file", description = "이미지URL", example = "https://mydiabucket.s3.ap-northeast-2.amazonaws.com/static/%EC%86%90%ED%9D%A5%EB%AF%BC.jpg"),
		@Parameter(name = "introduce", description = "PB 소개 문구", example = "소개 문구 예시입니다.")
	})
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "요청에 성공하였습니다.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseProfileDTO.class))),
		@ApiResponse(responseCode = "500", description = "프로필 업데이트 실패 혹은 이미지 저장 실패")
	})
	@PutMapping("/profile")
	public ResponseEntity<ResponseEditProfileDTO> updateProfile(
		@RequestParam(required = false) MultipartFile file,
		@RequestParam(required = false) String introduce,
		@Parameter(name = "hashtags", description = "PB의 해시태그 리스트", example = "[\"자산관리\",\"금융컨설팅\",\"포트폴리오\"]") @RequestParam List<String> hashtags,
		HttpServletRequest request) {
		// 세션 확인 코드 추가
		HttpSession session = request.getSession(false);
		if (session == null) { // 세션이 없으면 홈으로 이동
			return new ResponseEntity<>(null, HttpStatus.FOUND);
		}

		LoginDTO loginDTO = (LoginDTO)session.getAttribute(PbSessionConst.LOGIN_PB);
		if (loginDTO == null) { // 세션에 회원 데이터가 없으면 홈으로 이동
			return new ResponseEntity<>(null, HttpStatus.FOUND);
		}

		String imgUrl = null;
		if (file != null) {
			try {
				imgUrl = s3Service.upload(file, "profile");
			} catch (IOException e) {
				throw new GlobalException(ILLEGAL_S3_REQUEST);
			}
		}

		return ResponseEntity.ok()
			.body(pbProfileService.updateProfile(loginDTO.getPbId(), introduce, imgUrl, hashtags));
	}

	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "반환 성공"),
		@ApiResponse(responseCode = "400", description = "요청 본문이 비어 있거나 잘못된 형식입니다.")
	})
	@PutMapping("/availability")
	public ResponseEntity<RequestAvailabilityDTO> updateAvailability(
		@RequestBody RequestAvailabilityDTO availabilityDTO, HttpServletRequest request) {
		// 세션 확인 코드 추가
		HttpSession session = request.getSession(false);
		if (session == null) { // 세션이 없으면 홈으로 이동
			return new ResponseEntity<>(null, HttpStatus.FOUND);
		}

		LoginDTO loginDTO = (LoginDTO)session.getAttribute(PbSessionConst.LOGIN_PB);
		if (loginDTO == null) { // 세션에 회원 데이터가 없으면 홈으로 이동
			return new ResponseEntity<>(null, HttpStatus.FOUND);
		}

		RequestAvailabilityDTO updateAvailability = pbProfileService.updateAvailability(availabilityDTO);
		pbAvailabilityHandler.notifyClients(updateAvailability);
		return ResponseEntity.ok().body(updateAvailability);

	}

}
