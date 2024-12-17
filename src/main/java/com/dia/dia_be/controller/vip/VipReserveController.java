package com.dia.dia_be.controller.vip;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dia.dia_be.dto.vip.reserveDTO.RequestReserveDTO;
import com.dia.dia_be.dto.vip.reserveDTO.ResponseReserveDTO;
import com.dia.dia_be.dto.vip.reserveDTO.ResponseReserveInfoDTO;
import com.dia.dia_be.exception.GlobalException;
import com.dia.dia_be.service.vip.intf.VipReserveService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/vip/reserves")
@Tag(name = "상담 예약", description = "VIP 상담 예약 API")
public class VipReserveController {

	private final VipReserveService vipReserveService;

	public VipReserveController(VipReserveService vipReserveService) {
		this.vipReserveService = vipReserveService;
	}

	@PostMapping
	@Operation(summary = "VIP가 상담 예약 양식을 채워 상담 예약을 요청", description = "예약일, 예약시, 상담 제목, 상담 내용, 카테고리를 받아 예약 등록")
	@Parameters({
		@Parameter(name = "date", description = "상담희망일", example = "2024-12-30"),
		@Parameter(name = "time", description = "상담희망시", example = "14:00"),
		@Parameter(name = "categoryId", description = "카테고리ID", example = "2"),
		@Parameter(name = "title", description = "상담 제목", example = "퇴직연금에 가입하고 싶어요."),
		@Parameter(name = "content", description = "상담 내용", example = "퇴직이 가까워져옵니다...")
	})
	public ResponseEntity<?> addReserve(@RequestBody RequestReserveDTO requestReserveDTO) {
		final Long customerId = 1L;
		try {
			return ResponseEntity.ok(vipReserveService.addReserve(customerId, requestReserveDTO));
		} catch (GlobalException e) {
			return ResponseEntity.status(400).body(e.getMessage());
		} catch (Exception e) {
			return ResponseEntity.status(500).body(e.getMessage());
		}
	}

	@GetMapping("/info")
	@Operation(summary = "VIP의 예약 전 정보 조회", description = "VIP 이름과 PB 이름을 반환")
	public ResponseEntity<?> getReserveInfo() {
		final Long customerId = 1L;
		try {
			ResponseReserveInfoDTO response = vipReserveService.getInfo(customerId);
			return ResponseEntity.ok(response);
		} catch (GlobalException e) {
			return ResponseEntity.status(400).body(e.getMessage());
		} catch (Exception e) {
			return ResponseEntity.status(500).body(e.getMessage());
		}
	}

	@GetMapping
	@Operation(summary = "예정된 상담 예약 조회", description = "예약된 상담 정보를 반환합니다.")
	public ResponseEntity<?> getReserves() {
		final Long customerId = 1L;
		try {
			List<ResponseReserveDTO> reserves = vipReserveService.getReserves(customerId);
			return ResponseEntity.ok(reserves);
		} catch (Exception e) {
			return ResponseEntity.status(500).body("예약 조회 중 오류 발생: " + e.getMessage());
		}
	}

	@GetMapping("/{id}")
	@Operation(summary = "특정 상담 예약 조회", description = "예약된 상담 정보 중 하나를 반환합니다.")
	public ResponseEntity<?> getReserveById(@PathVariable("id") Long consultingId) {
		final Long customerId = 1L;
		try {
			return ResponseEntity.ok(vipReserveService.getReserveByConsultingId(consultingId));
		} catch (Exception e) {
			return ResponseEntity.status(500).body(e.getMessage());
		}
	}

	@DeleteMapping("/{id}")
	@Operation(summary = "예약 삭제", description = "예약을 삭제합니다.")
	public ResponseEntity<?> deleteReserve(@PathVariable("id") Long consultingId) {
		try {
			return ResponseEntity.ok(vipReserveService.deleteReserve(consultingId));
		} catch (GlobalException e) {
			return ResponseEntity.status(400).body(e.getMessage());
		} catch (Exception e) {
			return ResponseEntity.status(500).body(e.getMessage());
		}
	}
}
