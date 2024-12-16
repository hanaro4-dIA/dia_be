package com.dia.dia_be.service.pb.intf;

import java.util.List;

import com.dia.dia_be.dto.pb.ReservesDTO.ResponseReserveDTO;

public interface ReserveService {

	public List<ResponseReserveDTO> getApprovedReserves(boolean status);

	public String getContent(Long id);

}
