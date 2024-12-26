package com.dia.dia_be.controller.vip;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dia.dia_be.domain.VipSessionConst;
import com.dia.dia_be.dto.vip.loginDTO.VipLoginDTO;
import com.dia.dia_be.dto.vip.reserveDTO.RequestReserveDTO;
import com.dia.dia_be.global.session.SessionManager;
import com.dia.dia_be.service.vip.intf.VipReserveService;
import com.dia.dia_be.websocket.RequestConsultationHandler;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/vip/reserves")
@Tag(name = "VIP - 상담 예약", description = "VIP 상담 예약 API")
public class VipReserveController {

	private final VipReserveService vipReserveService;
	private final SessionManager sessionManager;
	private final RequestConsultationHandler requestConsultationHandler;

	public VipReserveController(VipReserveService vipReserveService, SessionManager sessionManager,
		RequestConsultationHandler requestConsultationHandler) {
		this.vipReserveService = vipReserveService;
		this.requestConsultationHandler = requestConsultationHandler;
		this.sessionManager = sessionManager;
	}

	@PostMapping
	@Operation(summary = "VIP가 상담 예약 양식을 채워 상담 예약을 요청", description = "예약일, 예약시, 상담 제목, 상담 내용, 카테고리를 받아 예약 등록")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "상담 예약 성공"),
		@ApiResponse(responseCode = "500", description = "잘못된 요청으로 인한 반환 실패")
	})
	public ResponseEntity<?> addReserve(@RequestBody RequestReserveDTO requestReserveDTO, HttpServletRequest request) {
		HttpSession session = sessionManager.getSession(request);
		if (session == null) {
			return new ResponseEntity<>("No session found", HttpStatus.FOUND);
		}

		// 세션에서 VIP 로그인 정보 가져오기
		VipLoginDTO loginDTO = (VipLoginDTO)session.getAttribute(VipSessionConst.LOGIN_VIP);
		if (loginDTO == null) {
			return new ResponseEntity<>("Can't find user data in session", HttpStatus.FOUND);
		}

		try {
			Long consultationId = vipReserveService.addReserve(loginDTO.getCustomerId(), requestReserveDTO);
			requestConsultationHandler.requestConsultation(
				vipReserveService.getReserveByIdIfNotApproved(consultationId));
			return ResponseEntity.ok(consultationId);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}

	}

	@GetMapping("/info")
	@Operation(summary = "VIP의 예약 전 정보 조회", description = "VIP 이름과 PB 이름을 반환")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "이름 조회 성공", content = @Content(mediaType = "application/json")),
		@ApiResponse(responseCode = "500", description = "잘못된 요청으로 인한 반환 실패", content = @Content(mediaType = "application/json"))
	})
	public ResponseEntity<?> getReserveInfo(HttpServletRequest request) {
		HttpSession session = sessionManager.getSession(request);
		if (session == null) {
			return new ResponseEntity<>("No session found", HttpStatus.FOUND);
		}

		// 세션에서 VIP 로그인 정보 가져오기
		VipLoginDTO loginDTO = (VipLoginDTO)session.getAttribute(VipSessionConst.LOGIN_VIP);
		if (loginDTO == null) {
			return new ResponseEntity<>("Can't find user data in session", HttpStatus.FOUND);
		}

		try {
			return ResponseEntity.ok(vipReserveService.getInfo(loginDTO.getCustomerId()));
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@GetMapping
	@Operation(summary = "예정된 상담 예약 조회", description = "예약된 상담 정보를 반환합니다.")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "상담 예약 목록 반환 성공", content = @Content(mediaType = "application/json")),
		@ApiResponse(responseCode = "404", description = "목록을 찾을 수 없음"),
		@ApiResponse(responseCode = "500", description = "잘못된 요청으로 인한 반환 실패")
	})
	public ResponseEntity<?> getReserves(HttpServletRequest request) {
		HttpSession session = sessionManager.getSession(request);
		if (session == null) {
			return new ResponseEntity<>("No session found", HttpStatus.FOUND);
		}

		// 세션에서 VIP 로그인 정보 가져오기
		VipLoginDTO loginDTO = (VipLoginDTO)session.getAttribute(VipSessionConst.LOGIN_VIP);
		if (loginDTO == null) {
			return new ResponseEntity<>("Can't find user data in session", HttpStatus.FOUND);
		}

		try {
			return ResponseEntity.ok(vipReserveService.getReserves(loginDTO.getCustomerId()));
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@GetMapping("/{id}")
	@Operation(summary = "특정 상담 예약 조회", description = "예약된 상담 정보 중 하나를 반환합니다.")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "특정 상담 예약 내역 반환 성공", content = @Content(mediaType = "application/json")),
		@ApiResponse(responseCode = "404", description = "내역을 찾을 수 없음"),
		@ApiResponse(responseCode = "500", description = "잘못된 요청으로 인한 반환 실패")
	})
	public ResponseEntity<?> getReserveById(
		@Parameter(description = "상담ID", required = true, example = "1") @PathVariable("id") Long consultingId,
		HttpServletRequest request) {
		HttpSession session = sessionManager.getSession(request);
		if (session == null) {
			return new ResponseEntity<>("No session found", HttpStatus.FOUND);
		}

		// 세션에서 VIP 로그인 정보 가져오기
		VipLoginDTO loginDTO = (VipLoginDTO)session.getAttribute(VipSessionConst.LOGIN_VIP);
		if (loginDTO == null) {
			return new ResponseEntity<>("Can't find user data in session", HttpStatus.FOUND);
		}

		try {
			return ResponseEntity.ok(
				vipReserveService.getReserveByConsultingId(loginDTO.getCustomerId(), consultingId));
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@DeleteMapping("/{id}")
	@Operation(summary = "예약 삭제", description = "예약을 삭제합니다.")
	public ResponseEntity<?> deleteReserve(
		@Parameter(description = "상담ID", required = true, example = "1") @PathVariable("id") Long consultingId,
		HttpServletRequest request) {
		HttpSession session = sessionManager.getSession(request);
		if (session == null) {
			return new ResponseEntity<>("No session found", HttpStatus.FOUND);
		}

		// 세션에서 VIP 로그인 정보 가져오기
		VipLoginDTO loginDTO = (VipLoginDTO)session.getAttribute(VipSessionConst.LOGIN_VIP);
		if (loginDTO == null) {
			return new ResponseEntity<>("Can't find user data in session", HttpStatus.FOUND);
		}

		try {
			return ResponseEntity.ok(vipReserveService.deleteReserve(loginDTO.getCustomerId(), consultingId));
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
}
