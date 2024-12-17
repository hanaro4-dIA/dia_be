package com.dia.dia_be.service.vip.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.dia.dia_be.domain.Category;
import com.dia.dia_be.dto.vip.categoryDTO.ResponseCategoryGetDTO;
import com.dia.dia_be.repository.CategoryRepository;
import com.dia.dia_be.service.vip.intf.VipCategoryService;

@Service
public class VipCategoryServiceImpl implements VipCategoryService {
	private final CategoryRepository categoryRepository;

	public VipCategoryServiceImpl(CategoryRepository categoryRepository) {
		this.categoryRepository = categoryRepository;
	}

	@Override
	public List<ResponseCategoryGetDTO> getCategories() {
		List<Category> categories = categoryRepository.findAll();
		return categories.stream().map(ResponseCategoryGetDTO::from).toList();
	}
}
