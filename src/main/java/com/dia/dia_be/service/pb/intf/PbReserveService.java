package com.dia.dia_be.service.pb.intf;

import java.util.List;

import com.dia.dia_be.dto.pb.reservesDTO.ResponseReserveDTO;


public interface PbReserveService {

	public List<ResponseReserveDTO> getApprovedReserves(boolean status);

	void approveReserve(Long id);
  
	public String getContent(Long id);
}
