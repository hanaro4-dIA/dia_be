package com.dia.dia_be.service.vip.intf;

import com.dia.dia_be.dto.vip.loginDTO.RequestVipSignUpDTO;

public interface VipLoginService {
	public Long signupProcess(RequestVipSignUpDTO requestVipSignUpDTO);
}
