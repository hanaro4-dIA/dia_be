package com.dia.dia_be.service.vip.intf;
import com.dia.dia_be.domain.Customer;
import com.dia.dia_be.dto.vip.loginDTO.RequestVipSignUpDTO;

import com.dia.dia_be.dto.vip.loginDTO.VipLoginForm;

public interface VipLoginService {
	public Long signupProcess(RequestVipSignUpDTO requestVipSignUpDTO);
	Customer checkLogin(VipLoginForm vipLoginForm);
}
