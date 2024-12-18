package com.dia.dia_be.service.pb.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.dia.dia_be.domain.Customer;
import com.dia.dia_be.dto.pb.customerDTO.ResponseCustomerDTO;
import com.dia.dia_be.exception.GlobalException;
import com.dia.dia_be.exception.PbErrorCode;
import com.dia.dia_be.repository.CustomerRepository;
import com.dia.dia_be.service.pb.intf.PbCustomerService;

@Service
public class PbCustomerServiceImpl implements PbCustomerService {

	private final CustomerRepository customerRepository;

	public PbCustomerServiceImpl(CustomerRepository customerRepository) {
		this.customerRepository = customerRepository;
	}

	@Override
	public List<ResponseCustomerDTO> getCustomerList() {
		List<Customer> customers = customerRepository.findAll();
		if (customers.isEmpty()) {
			throw new GlobalException(PbErrorCode.CUSTOMER_NOT_FOUND);
		}
		return customers.stream()
			.map(this::convertToDto)
			.collect(Collectors.toList());
	}

	// pbId에 따른 고객 리스트 조회 메서드
	@Override
	public List<ResponseCustomerDTO> getCustomerListByPbId(Long pbId) {
		if (pbId == null || pbId <= 0) {
			throw new GlobalException(PbErrorCode.PROFILE_NOT_FOUND);
		}

		List<Customer> customers = customerRepository.findByPbId(pbId);
		if (customers.isEmpty()) {
			throw new GlobalException(PbErrorCode.CUSTOMER_NOT_FOUND);
		}

		return customers.stream()
			.map(this::convertToDto)
			.collect(Collectors.toList());
	}

	@Override
	public List<ResponseCustomerDTO> searchCustomer(String name) {
		if (name == null || name.trim().isEmpty()) {
			throw new GlobalException(PbErrorCode.INVALID_CUSTOMER_SEARCH);
		}
		List<Customer> customers = customerRepository.findByNameContaining(name);
		if (customers.isEmpty()) {
			throw new GlobalException(PbErrorCode.CUSTOMER_NOT_FOUND);
		}
		return customers.stream()
			.map(this::convertToDto)
			.collect(Collectors.toList());
	}

	@Override
	public ResponseCustomerDTO getCustomerDetail(Long customerId) {
		if (customerId == null || customerId <= 0) {
			throw new GlobalException(PbErrorCode.INVALID_CUSTOMER_SEARCH);
		}

		Optional<Customer> customerOptional = customerRepository.findById(customerId);
		if (customerOptional.isPresent()) {
			return convertToDto(customerOptional.get());
		} else {
			throw new GlobalException(PbErrorCode.CUSTOMER_NOT_FOUND);
		}
	}

	@Override
	public ResponseCustomerDTO updateCustomerMemo(Long customerId, String memo) {
		if (customerId == null || customerId <= 0) {
			throw new GlobalException(PbErrorCode.INVALID_CUSTOMER_SEARCH);
		}

		Optional<Customer> customerOptional = customerRepository.findById(customerId);
		if (customerOptional.isPresent()) {
			Customer customer = customerOptional.get();
			customer.update(memo);
			customerRepository.save(customer);
			return ResponseCustomerDTO.toDto(customer);
		} else {
			throw new GlobalException(PbErrorCode.CUSTOMER_NOT_FOUND);
		}
	}


	private ResponseCustomerDTO convertToDto(Customer customer) {
		ResponseCustomerDTO dto = new ResponseCustomerDTO();
		dto.setId(customer.getId());
		dto.setPbId(customer.getPb().getId());
		dto.setPassword(customer.getPassword());
		dto.setDate(customer.getDate());
		dto.setCount(customer.getCount());
		dto.setMemo(customer.getMemo());
		dto.setEmail(customer.getEmail());
		dto.setName(customer.getName());
		dto.setTel(customer.getTel());
		dto.setAddress(customer.getAddress());

		return dto;
	}
}
