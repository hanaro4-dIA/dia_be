package com.dia.dia_be.service.vip.intf;

import com.dia.dia_be.dto.vip.pbProfileDTO.ResponsePbProfileDTO;

public interface VipPbService {

	public ResponsePbProfileDTO getPbProfile(Long customerId);
}
