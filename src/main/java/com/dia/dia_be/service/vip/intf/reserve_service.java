package com.dia.dia_be.service.vip.intf;

import java.util.List;

import com.dia.dia_be.dto.vip.Reserve_dto;
import com.dia.dia_be.dto.vip.Reserve_info_dto;
import com.dia.dia_be.dto.vip.Reserve_post_dto;
import com.dia.dia_be.dto.vip.Reserve_simple_dto;

public interface reserve_service {
	public List<Reserve_simple_dto> getReserves(Long vipId);

	public Reserve_info_dto getReserveInfo(Long vipId);

	public void postReserve(Long vipId, Reserve_post_dto dto);

	public Reserve_dto getReserveConfirm(Long reserveId);
}
