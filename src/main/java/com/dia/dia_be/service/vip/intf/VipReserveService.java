package com.dia.dia_be.service.vip.intf;

import java.util.List;

import com.dia.dia_be.dto.vip.reserveDTO.RequestReserveDTO;
import com.dia.dia_be.dto.vip.reserveDTO.ResponseReserveDTO;
import com.dia.dia_be.dto.vip.reserveDTO.ResponseReserveInfoDTO;

public interface VipReserveService {

	public Long addReserve(Long customerId, RequestReserveDTO requestReserveDTO);

	public ResponseReserveInfoDTO getInfo(Long customerId);

	public List<ResponseReserveDTO> getReserves(Long customerId);

	public ResponseReserveDTO getReserveByConsultingId(Long consultingId);

}
