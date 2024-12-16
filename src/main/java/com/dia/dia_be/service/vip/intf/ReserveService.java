package com.dia.dia_be.service.vip.intf;

import com.dia.dia_be.dto.vip.RequestReservePostDTO;

public interface ReserveService {

	public Long addReserve(Long customerId, RequestReservePostDTO requestReservePostDTO);
}
