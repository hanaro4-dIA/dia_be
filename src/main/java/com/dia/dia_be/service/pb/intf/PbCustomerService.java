package com.dia.dia_be.service.pb.intf;

import java.util.List;

import com.dia.dia_be.dto.pb.customerDTO.CustomerDTO;

public interface PbCustomerService {

	List<CustomerDTO> getCustomerList();

	CustomerDTO getCustomerDetail(Long customerId);

	List<CustomerDTO> searchCustomer(String name);

	CustomerDTO updateCustomerMemo(Long customerId, String memo);
}
