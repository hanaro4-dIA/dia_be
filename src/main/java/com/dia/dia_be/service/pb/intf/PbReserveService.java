package com.dia.dia_be.service.pb.intf;

import java.time.LocalDate;
import java.util.List;

import com.dia.dia_be.dto.pb.reservesDTO.ResponseReserveByDateDTO;
import com.dia.dia_be.dto.pb.reservesDTO.ResponseReserveDTO;

public interface PbReserveService {

	public List<ResponseReserveDTO> getApprovedReserves(boolean status);

	void approveReserve(Long id);

	public String getContent(Long id);

	public List<ResponseReserveByDateDTO> getReservesByDate(LocalDate date, Long pbId);
}
