package com.dia.dia_be.service.vip.intf;
import com.dia.dia_be.dto.vip.loginDTO.RequestVipSignUpDTO;

import com.dia.dia_be.dto.vip.loginDTO.RequestVipSignUpDTO;

import com.dia.dia_be.dto.vip.profileDTO.LoginForm;

public interface VipLoginService {
	public Long signupProcess(RequestVipSignUpDTO requestVipSignUpDTO);
	void checkLogin(LoginForm loginForm);
}
