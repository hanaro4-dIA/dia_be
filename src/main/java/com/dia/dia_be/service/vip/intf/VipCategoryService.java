package com.dia.dia_be.service.vip.intf;

import java.util.List;

import com.dia.dia_be.dto.vip.categoryDTO.ResponseCategoryDTO;

public interface VipCategoryService {

	public List<ResponseCategoryDTO> getCategories();
}
