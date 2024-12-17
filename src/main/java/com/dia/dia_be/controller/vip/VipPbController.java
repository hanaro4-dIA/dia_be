package com.dia.dia_be.controller.vip;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dia.dia_be.service.vip.intf.VipPbService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/vip/pb")
@Tag(name = "VIP의 PB 정보", description = "VIP에게 배정된 PB 정보 API")
public class VipPbController {

	private final VipPbService vipPbService;

	public VipPbController(VipPbService vipPbService) {
		this.vipPbService = vipPbService;
	}

	@GetMapping
	@Operation(summary = "PB 프로필 조회", description = "특정 손님의 PB 프로필 정보를 조회합니다.")
	public ResponseEntity<?> getPbProfile() {
		final Long customerId = 1L;
		try {
			return ResponseEntity.ok(vipPbService.getPbProfile(customerId));
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
}
