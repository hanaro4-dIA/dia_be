package com.dia.dia_be.service.pb.intf;

import java.util.List;

import com.dia.dia_be.dto.pb.customerDTO.ResponseCustomerDTO;

public interface PbCustomerService {

	List<ResponseCustomerDTO> getCustomerList();

	ResponseCustomerDTO getCustomerDetail(Long customerId);

	List<ResponseCustomerDTO> searchCustomer(Long pbId, String name);

	ResponseCustomerDTO updateCustomerMemo(Long customerId, String memo);

	List<ResponseCustomerDTO> getCustomerListByPbId(Long pbId);
}
