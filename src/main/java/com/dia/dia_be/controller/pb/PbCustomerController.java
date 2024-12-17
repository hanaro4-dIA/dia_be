package com.dia.dia_be.controller.pb;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dia.dia_be.dto.pb.customerDTO.CustomerDTO;
import com.dia.dia_be.service.pb.intf.PbCustomerService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

//pb = 1
//customer = 1
//로 디폴트 해둔 상태
@RestController
// @RequestMapping("/pb/customers")
@RequestMapping("/1/customers")
@Tag(name = "Customer", description = "Customer API")
public class PbCustomerController {

	private final PbCustomerService customer_service;

	public PbCustomerController(PbCustomerService pbCustomerService) {
		this.customer_service = pbCustomerService;
	}

	// {{base_url}}/pb/customers/list
	@GetMapping("/list")
	@Operation(summary = "Customer 리스트 조회", description = "모든 Customer 리스트를 조회합니다.")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "Customer 리스트 조회 성공", content = @Content(mediaType = "application/json")),
		@ApiResponse(responseCode = "500", description = "서버 오류")
	})
	public ResponseEntity<List<CustomerDTO>> getCustomerList() {
		List<CustomerDTO> customers = customer_service.getCustomerList();
		return ResponseEntity.ok(customers);
	}

	// {{base_url}}/pb/customers/search?name={{customerName}}
	@GetMapping("/search")
	@Operation(summary = "Customer 검색", description = "Customer 이름으로 검색합니다.")
	@Parameters({
		@Parameter(name = "name", description = "검색할 Customer의 이름", example = "강재준")
	})
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "검색 성공", content = @Content(mediaType = "application/json")),
		@ApiResponse(responseCode = "404", description = "검색 결과 없음")
	})
	public ResponseEntity<List<CustomerDTO>> searchCustomer(@RequestParam(name = "name") String name) {
		List<CustomerDTO> customers = customer_service.searchCustomer(name);
		return ResponseEntity.ok(customers);
	}

	// {{base_url}}/pb/customers/list/{{customerId}}
	@GetMapping("/list/{customerId}")
	@Operation(summary = "특정 Customer 정보 조회", description = "특정 Customer의 상세 정보를 조회합니다.")
	@Parameter(name = "customerId", description = "Customer의 ID", example = "1")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "Customer 정보 조회 성공", content = @Content(mediaType = "application/json")),
		@ApiResponse(responseCode = "404", description = "Customer가 존재하지 않음")
	})
	public ResponseEntity<CustomerDTO> getCustomerDetail(@PathVariable("customerId") Long customerId) {
		// return ResponseEntity.ok(customer_service.getCustomerDetail(customerId));
		return ResponseEntity.ok(customer_service.getCustomerDetail(1L));
	}

	// {{base_url}}/pb/customers/{{customerId}}/memo
	@PostMapping("/{customerId}/memo")
	@Operation(summary = "Customer 메모 수정", description = "Customer의 메모를 수정합니다.")
	@Parameters({
		@Parameter(name = "customerId", description = "Customer의 고유 ID", example = "1"),
		@Parameter(name = "memo", description = "새로운 메모 내용", example = "변경된 메모")
	})
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "메모 수정 성공", content = @Content(mediaType = "application/json")),
		@ApiResponse(responseCode = "404", description = "Customer이 존재하지 않음")
	})
	public ResponseEntity<CustomerDTO> updateCustomerMemo(
		@PathVariable("customerId") Long customerId,
		@RequestParam("memo") String memo) {
		// CustomerDTO updatedCustomer = customer_service.updateCustomerMemo(customerId, memo);
		CustomerDTO updatedCustomer = customer_service.updateCustomerMemo(1L, memo);
		return ResponseEntity.ok(updatedCustomer);
	}

}
