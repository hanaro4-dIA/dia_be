package com.dia.dia_be.controller.vip;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dia.dia_be.dto.vip.RequestReservePostDTO;
import com.dia.dia_be.exception.GlobalException;
import com.dia.dia_be.service.vip.intf.ReserveService;

@RestController
@RequestMapping("/vip/reserve")
public class ReserveController {

	private final ReserveService reserveService;

	public ReserveController(ReserveService reserveService) {
		this.reserveService = reserveService;
	}

	@PostMapping
	public ResponseEntity<?> addReserve(@RequestBody RequestReservePostDTO requestReservePostDTO) {
		final Long customerId = 1L;
		try {
			return ResponseEntity.ok(reserveService.addReserve(customerId, requestReservePostDTO));
		} catch (GlobalException e) {
			return ResponseEntity.status(400).body(e.getMessage());
		} catch (Exception e) {
			return ResponseEntity.status(500).body(e.getMessage());
		}
	}

}
