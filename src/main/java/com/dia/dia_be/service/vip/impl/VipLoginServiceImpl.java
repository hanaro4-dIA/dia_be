package com.dia.dia_be.service.vip.impl;

import java.time.LocalDate;

import org.springframework.stereotype.Service;

import com.dia.dia_be.domain.Customer;
import com.dia.dia_be.domain.Pb;
import com.dia.dia_be.dto.vip.loginDTO.RequestVipSignUpDTO;
import com.dia.dia_be.exception.GlobalException;
import com.dia.dia_be.exception.VipErrorCode;
import com.dia.dia_be.repository.CustomerRepository;
import com.dia.dia_be.repository.PbRepository;
import com.dia.dia_be.service.vip.intf.VipLoginService;

@Service
public class VipLoginServiceImpl implements VipLoginService {

	private final CustomerRepository customerRepository;
	private final PbRepository pbRepository;

	public VipLoginServiceImpl(CustomerRepository customerRepository, PbRepository pbRepository){
		this.customerRepository = customerRepository;
		this.pbRepository = pbRepository;
	}

	@Override
	public Long signupProcess(RequestVipSignUpDTO requestVipSignUpDTO) {
		Customer customer = Customer.builder()
			.name(requestVipSignUpDTO.getName())
			.email(requestVipSignUpDTO.getEmail())
			.password(requestVipSignUpDTO.getPassword())
			.tel(requestVipSignUpDTO.getTel())
			.address(requestVipSignUpDTO.getAddress())
			.date(LocalDate.now())
			.pb(pbRepository.findById(1L).orElseThrow())
			.build();

		try{
			Customer savedCustomer = customerRepository.save(customer);
			return savedCustomer.getId();
		}catch (Exception e){
			throw new GlobalException(VipErrorCode.INVALID_SIGNUP_REQUEST);
		}
	}
}
