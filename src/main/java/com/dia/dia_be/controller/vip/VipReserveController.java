package com.dia.dia_be.controller.vip;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dia.dia_be.dto.vip.RequestReservePostDTO;
import com.dia.dia_be.exception.GlobalException;
import com.dia.dia_be.service.vip.intf.VipReserveService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/vip/reserves")
public class VipReserveController {

	private final VipReserveService vipReserveService;

<<<<<<< HEAD
	public VipReserveController(ReserveService reserveService) {
		this.reserveService = reserveService;
=======
	public VipReserveController(VipReserveService vipReserveService) {
		this.vipReserveService = vipReserveService;
>>>>>>> 9096336 ([style] : conflict를 막기 위한 폴더/파일명 수정)
	}

	@PostMapping
	@Tag(name = "상담 예약하기", description = "상담 예약 페이지 상담 예약 API")
	@Operation(summary = "VIP가 상담 예약 양식을 채워 상담 예약을 요청", description = "예약일, 예약시, 상담 제목, 상담 내용, 카테고리를 받아 예약 등록")
	@Parameters({
		@Parameter(name = "date", description = "상담희망일", example = "2024-12-30"),
		@Parameter(name = "time", description = "상담희망시", example = "14:00"),
		@Parameter(name = "categoryId", description = "카테고리ID", example = "2"),
		@Parameter(name = "title", description = "상담 제목", example = "퇴직연금에 가입하고 싶어요."),
		@Parameter(name = "content", description = "상담 내용", example = "퇴직이 가까워져옵니다...")
	})
	public ResponseEntity<?> addReserve(@RequestBody RequestReservePostDTO requestReservePostDTO) {
		final Long customerId = 1L;
		try {
			return ResponseEntity.ok(vipReserveService.addReserve(customerId, requestReservePostDTO));
		} catch (GlobalException e) {
			return ResponseEntity.status(400).body(e.getMessage());
		} catch (Exception e) {
			return ResponseEntity.status(500).body(e.getMessage());
		}
	}

}
