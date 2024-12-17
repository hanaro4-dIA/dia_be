package com.dia.dia_be.controller.vip;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dia.dia_be.service.vip.intf.VipCategoryService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/vip/categories")
public class VipCategoryController {

	private final VipCategoryService vipCategoryService;

	public VipCategoryController(VipCategoryService vipCategoryService) {
		this.vipCategoryService = vipCategoryService;
	}

	@GetMapping
	@Tag(name = "카테고리 가져오기", description = "카테고리 전체 load")
	@Operation(summary = "카테고리 id와 name을 가져오는 API")
	@Parameters({
		@Parameter(name = "id", description = "아이디", example = "2"),
		@Parameter(name = "name", description = "카테고리명", example = "은퇴설계")
	})
	public ResponseEntity<?> findAll() {
		try {
			return ResponseEntity.ok(vipCategoryService.getCategories());
		} catch (Exception e) {
			return ResponseEntity.status(500).body(e.getMessage());
		}
	}
}
