package com.dia.dia_be.controller.vip;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dia.dia_be.dto.vip.categoryDTO.ResponseCategoryDTO;
import com.dia.dia_be.service.vip.intf.VipCategoryService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/vip/categories")
@Tag(name = "VIP - 기타 조회 관련 API", description = "PB 프로필, 카테고리 목록")
public class VipCategoryController {

	private final VipCategoryService vipCategoryService;

	public VipCategoryController(VipCategoryService vipCategoryService) {
		this.vipCategoryService = vipCategoryService;
	}

	@GetMapping
	@Operation(summary = "카테고리 id와 name을 가져오는 API")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "카테고리 반환 성공"),
		@ApiResponse(responseCode = "500", description = "카테고리 반환 실패")
	})
	public ResponseEntity<List<ResponseCategoryDTO>> findAll() {
		return ResponseEntity.ok(vipCategoryService.getCategories());
	}
}
