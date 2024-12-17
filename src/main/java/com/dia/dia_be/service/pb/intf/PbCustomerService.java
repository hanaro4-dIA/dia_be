package com.dia.dia_be.service.pb.intf;

import java.util.List;

import com.dia.dia_be.dto.pb.customerDTO.ResponseCustomerDTO;

public interface PbCustomerService {

	List<ResponseCustomerDTO> getCustomerList();

	ResponseCustomerDTO getCustomerDetail(Long customerId);

	List<ResponseCustomerDTO> searchCustomer(String name);

	ResponseCustomerDTO updateCustomerMemo(Long customerId, String memo);
}
