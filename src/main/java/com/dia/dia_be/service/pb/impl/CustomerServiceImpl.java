package com.dia.dia_be.service.pb.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.dia.dia_be.domain.Customer;
import com.dia.dia_be.dto.pb.CustomerDTO;
import com.dia.dia_be.exception.GlobalException;
import com.dia.dia_be.exception.PbErrorCode;
import com.dia.dia_be.repository.CustomerRepository;
import com.dia.dia_be.service.pb.intf.CustomerService;

@Service
public class CustomerServiceImpl implements CustomerService {

	private final CustomerRepository customerRepository;

	public CustomerServiceImpl(CustomerRepository customerRepository) {
		this.customerRepository = customerRepository;
	}

	@Override
	public List<CustomerDTO> getCustomerList() {
		List<Customer> customers = customerRepository.findAll();
		if (customers.isEmpty()) {
			throw new GlobalException(PbErrorCode.CUSTOMER_NOT_FOUND);
		}
		return customers.stream()
			.map(this::convertToDto)
			.collect(Collectors.toList());
	}

	@Override
	public List<CustomerDTO> searchCustomer(String name) {
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
	public CustomerDTO getCustomerDetail(Long customerId) {
		if (customerId == null || customerId <= 0) {
			throw new GlobalException(PbErrorCode.INVALID_CUSTOMER_SEARCH);
		}

		Optional<Customer> customerOptional = customerRepository.findById(customerId);
		if (customerOptional.isPresent()) {
			return convertToDto(customerOptional.get());
		} else {
			throw new GlobalException(PbErrorCode.CUSTOMER_NOT_FOUND);  // 고객을 찾을 수 없는 경우
		}
	}

	@Override
	public CustomerDTO updateCustomerMemo(Long customerId, String memo) {
		if (customerId == null || customerId <= 0) {
			throw new GlobalException(PbErrorCode.INVALID_CUSTOMER_SEARCH);
		}

		Optional<Customer> customerOptional = customerRepository.findById(customerId);
		if (customerOptional.isPresent()) {
			Customer customer = customerOptional.get();
			// update 메서드를 사용하여 memo 수정
			customer.update(memo);
			customerRepository.save(customer); // 메모 수정 후 저장
			return convertToDto(customer); // 수정된 고객 정보를 DTO로 변환하여 반환
		} else {
			throw new GlobalException(PbErrorCode.CUSTOMER_NOT_FOUND);  // 고객을 찾을 수 없는 경우
		}
	}

	private CustomerDTO convertToDto(Customer customer) {
		CustomerDTO dto = new CustomerDTO();
		dto.setId(customer.getId());
		dto.setPbId(customer.getPb().getId());
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
