package com.dia.dia_be.controller.pb;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dia.dia_be.domain.PbSessionConst;
import com.dia.dia_be.dto.pb.customerDTO.RequestCustomerDTO;
import com.dia.dia_be.dto.pb.customerDTO.ResponseCustomerDTO;
import com.dia.dia_be.dto.pb.loginDTO.LoginDTO;
import com.dia.dia_be.service.pb.intf.PbCustomerService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/pb/customers")
// @RequestMapping("/1/customers")
@Tag(name = "PbCustomer", description = "Customer API")
public class PbCustomerController {

	private final PbCustomerService pbCustomerService;

	public PbCustomerController(PbCustomerService pbCustomerService) {
		this.pbCustomerService = pbCustomerService;
	}

	// GET {{base_url}}/pb/customers/list?pbId={{pbId}}
	@GetMapping("/list")
	@Operation(summary = "Customer 리스트 조회", description = "주어진 pbId에 따른 Customer 리스트를 조회합니다.")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "Customer 리스트 조회 성공", content = @Content(mediaType = "application/json")),
		@ApiResponse(responseCode = "500", description = "서버 오류")
	})
	public ResponseEntity<List<ResponseCustomerDTO>> getCustomerList( HttpServletRequest request ) {
		// 세션 확인 코드 추가
		HttpSession session = request.getSession(false);
		if (session == null) { // 세션이 없으면 홈으로 이동
			return new ResponseEntity<>(null, HttpStatus.FOUND);
		}

		LoginDTO loginDTO =  (LoginDTO) session.getAttribute(PbSessionConst.LOGIN_PB);
		if (loginDTO == null) { // 세션에 회원 데이터가 없으면 홈으로 이동
			return new ResponseEntity<>(null, HttpStatus.FOUND);
		}

		List<ResponseCustomerDTO> customers = pbCustomerService.getCustomerListByPbId(loginDTO.getPbId());
		return ResponseEntity.ok(customers);
	}

	// GET {{base_url}}/pb/customers/search?name={{customerName}}
	@GetMapping("/search")
	@Operation(summary = "Customer 검색", description = "Customer 이름으로 검색합니다.")
	@Parameters({
		@Parameter(name = "name", description = "검색할 Customer의 이름", example = "강재준")
	})
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "검색 성공", content = @Content(mediaType = "application/json")),
		@ApiResponse(responseCode = "404", description = "검색 결과 없음")
	})
	public ResponseEntity<List<ResponseCustomerDTO>> searchCustomer(@RequestParam(name = "name") String name, HttpServletRequest request) {
		// 세션 확인 코드 추가
		HttpSession session = request.getSession(false);
		if (session == null) { // 세션이 없으면 홈으로 이동
			return new ResponseEntity<>(null, HttpStatus.FOUND);
		}

		LoginDTO loginDTO =  (LoginDTO) session.getAttribute(PbSessionConst.LOGIN_PB);
		if (loginDTO == null) { // 세션에 회원 데이터가 없으면 홈으로 이동
			return new ResponseEntity<>(null, HttpStatus.FOUND);
		}

		List<ResponseCustomerDTO> customers = pbCustomerService.searchCustomer(name);
		return ResponseEntity.ok(customers);
	}

	// GET {{base_url}}/pb/customers/list/{{customerId}}
	@GetMapping("/list/{customerId}")
	@Operation(summary = "특정 Customer 정보 조회", description = "특정 Customer의 상세 정보를 조회합니다.")
	@Parameter(name = "customerId", description = "Customer의 ID", example = "1")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "Customer 정보 조회 성공", content = @Content(mediaType = "application/json")),
		@ApiResponse(responseCode = "404", description = "Customer가 존재하지 않음")
	})
	public ResponseEntity<ResponseCustomerDTO> getCustomerDetail(@PathVariable("customerId") Long customerId, HttpServletRequest request) {
		// 세션 확인 코드 추가
		HttpSession session = request.getSession(false);
		if (session == null) { // 세션이 없으면 홈으로 이동
			return new ResponseEntity<>(null, HttpStatus.FOUND);
		}

		LoginDTO loginDTO =  (LoginDTO) session.getAttribute(PbSessionConst.LOGIN_PB);
		if (loginDTO == null) { // 세션에 회원 데이터가 없으면 홈으로 이동
			return new ResponseEntity<>(null, HttpStatus.FOUND);
		}

		return ResponseEntity.ok(pbCustomerService.getCustomerDetail(customerId));
		// return ResponseEntity.ok(pbCustomerService.getCustomerDetail(1L));
	}

	// POST {{base_url}}/pb/customers/{{customerId}}/memo
	@PostMapping("/{customerId}/memo")
	@Operation(summary = "Customer 메모 수정", description = "Customer의 메모를 수정합니다.")
	@Parameters({
		@Parameter(name = "customerId", description = "Customer의 고유 ID", example = "1")
	})
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "메모 수정 성공", content = @Content(mediaType = "application/json")),
		@ApiResponse(responseCode = "404", description = "Customer가 존재하지 않음")
	})
	public ResponseEntity<ResponseCustomerDTO> updateCustomerMemo(
		@PathVariable("customerId") Long customerId,
		@RequestBody RequestCustomerDTO requestDto,
		HttpServletRequest request) {
		// 세션 확인 코드 추가
		HttpSession session = request.getSession(false);
		if (session == null) { // 세션이 없으면 홈으로 이동
			return new ResponseEntity<>(null, HttpStatus.FOUND);
		}

		LoginDTO loginDTO =  (LoginDTO) session.getAttribute(PbSessionConst.LOGIN_PB);
		if (loginDTO == null) { // 세션에 회원 데이터가 없으면 홈으로 이동
			return new ResponseEntity<>(null, HttpStatus.FOUND);
		}

		ResponseCustomerDTO updatedCustomer = pbCustomerService.updateCustomerMemo(customerId, requestDto.getMemo());
		// ResponseCustomerDTO updatedCustomer = pbCustomerService.updateCustomerMemo(1L, requestDto.getMemo());
		return ResponseEntity.ok(updatedCustomer);
	}


}
