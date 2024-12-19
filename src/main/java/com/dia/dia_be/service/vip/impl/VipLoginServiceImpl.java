package com.dia.dia_be.service.vip.impl;

import org.springframework.stereotype.Service;

import com.dia.dia_be.domain.Customer;
import com.dia.dia_be.dto.vip.loginDTO.RequestVipSignUpDTO;
import com.dia.dia_be.exception.GlobalException;
import com.dia.dia_be.exception.VipErrorCode;
import com.dia.dia_be.repository.CustomerRepository;
import com.dia.dia_be.service.vip.intf.VipLoginService;

@Service
public class VipLoginServiceImpl implements VipLoginService {

	private final CustomerRepository customerRepository;

	public VipLoginServiceImpl(CustomerRepository customerRepository){
		this.customerRepository = customerRepository;
	}

	@Override
	public Long signupProcess(RequestVipSignUpDTO requestVipSignUpDTO) {
		Customer customer = Customer.builder()
			.name(requestVipSignUpDTO.getName())
			.email(requestVipSignUpDTO.getEmail())
			.password(requestVipSignUpDTO.getPassword())
			.tel(requestVipSignUpDTO.getTel())
			.address(requestVipSignUpDTO.getAddress())
			.build();

		try{
			Customer savedCustomer = customerRepository.save(customer);
			return savedCustomer.getId();
		}catch (Exception e){
			throw new GlobalException(VipErrorCode.INVALID_SIGNUP_REQUEST);
		}
	}
}
