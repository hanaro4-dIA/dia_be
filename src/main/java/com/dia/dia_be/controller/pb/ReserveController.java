package com.dia.dia_be.controller.pb;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dia.dia_be.dto.pb.ReservesDTO.ResponseReserveDTO;
import com.dia.dia_be.service.pb.intf.ReserveService;

@RestController
@RequestMapping("/pb/reserves")
public class ReserveController {
	private final ReserveService reserveService;

	public ReserveController(ReserveService reserveService) {
		this.reserveService = reserveService;
	}

	@GetMapping()
	public List<ResponseReserveDTO> getReserves(@RequestParam boolean status) {
		return reserveService.getApprovedReserves(status);
	}
}
