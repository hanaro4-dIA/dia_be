package com.dia.dia_be.service.vip.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.dia.dia_be.domain.Customer;
import com.dia.dia_be.domain.Hashtag;
import com.dia.dia_be.domain.Pb;
import com.dia.dia_be.dto.vip.pbProfileDTO.ResponsePbProfileDTO;
import com.dia.dia_be.repository.CustomerRepository;
import com.dia.dia_be.service.vip.intf.VipPbService;

@Service
public class VipPbServiceImpl implements VipPbService {
	private final CustomerRepository customerRepository;

	public VipPbServiceImpl(CustomerRepository customerRepository) {
		this.customerRepository = customerRepository;
	}

	@Override
	public ResponsePbProfileDTO getPbProfile(Long customerId) {
		Customer customer = customerRepository.findById(customerId).orElseThrow();
		Pb pb = customer.getPb();
		List<String> pbTags = pb.getHashtag().stream().map(Hashtag::getName).toList();
		return new ResponsePbProfileDTO(pb.getName(), pb.getIntroduce(), customer.getDate(), pb.getOffice(),
			pb.getTel(), pb.getCareer(), pb.getImageUrl(), pbTags, pb.isAvailability());
	}
}
