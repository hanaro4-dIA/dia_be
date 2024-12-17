package com.dia.dia_be.service.vip.intf;

import java.util.List;

import com.dia.dia_be.dto.vip.categoryDTO.ResponseCategoryGetDTO;

public interface VipCategoryService {

	public List<ResponseCategoryGetDTO> getCategories();
}
