package com.dia.dia_be.controller.pb;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dia.dia_be.domain.PbSessionConst;
import com.dia.dia_be.dto.pb.loginDTO.LoginDTO;
import com.dia.dia_be.dto.pb.reservesDTO.ResponseReserveByDateDTO;
import com.dia.dia_be.dto.pb.reservesDTO.ResponseReserveDTO;
import com.dia.dia_be.service.pb.intf.PbReserveService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/pb/reserves")
public class PbReserveController {

	private final PbReserveService pbReserveService;

	public PbReserveController(PbReserveService pbReserveService) {
		this.pbReserveService = pbReserveService;
	}

	@GetMapping
	@Tag(name = "들어온 상담 요청 관리", description = "PB의 상담 요청 관리 API")
	@Operation(summary = "들어온 상담 요청 조회", description = "들어온 상담 요청 조회 및 캘린더 내 전체 상담 일정 조회")
	@Parameters({
		@Parameter(name = "status", description = "상담 요청 승인 여부 상태", example = "false"),
		@Parameter(name = "type", description = "예정된 상담 일정", example = "upcoming")
	})
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "요청에 성공하였습니다.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseReserveDTO.class))),
		@ApiResponse(responseCode = "404", description = "검색 결과 없음")
	})
	public ResponseEntity<List<ResponseReserveDTO>> getReserves(@RequestParam boolean status,
		@RequestParam(required = false) String type, @RequestParam(required = false) Long customerId, HttpServletRequest request) {
		// 세션 확인 코드 추가
		HttpSession session = request.getSession(false);
		if (session == null) { // 세션이 없으면 홈으로 이동
			return new ResponseEntity<>(HttpStatus.FOUND);
		}

		LoginDTO loginDTO = (LoginDTO) session.getAttribute(PbSessionConst.LOGIN_PB);
		if (loginDTO == null) { // 세션에 회원 데이터가 없으면 홈으로 이동
			return new ResponseEntity<>(HttpStatus.FOUND);
		}


		List<ResponseReserveDTO> reserves = new ArrayList<>();

		// status가 true이고 type=upcoming인 경우
		if (status && "upcoming".equalsIgnoreCase(type)) {
			reserves = pbReserveService.getUpcomingReserves();
		} else if (status){
			reserves = pbReserveService.getApprovedReserves(status);
		} else if (customerId != null){
			reserves = pbReserveService.getUpcommingReservesWithCustomerId(customerId);
		}

		if (reserves.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<>(reserves, HttpStatus.OK);
	}


	@PutMapping
	@Tag(name = "들어온 상담 요청 관리", description = "PB의 상담 요청 관리 API")
	@Operation(summary = "상담 요청 승인", description = "아직 승인받지 않은 상담 요청을 승인 상태로 변경")
	@Parameters({
		@Parameter(name = "id", description = "승인할 상담 요청의 ID", example = "11")
	})
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "요청에 성공하였습니다."),
		@ApiResponse(responseCode = "400", description = "유효하지 않은 요청"),
		@ApiResponse(responseCode = "404", description = "상담 요청을 찾을 수 없음"),
		@ApiResponse(responseCode = "500", description = "이미 승인된 요청입니다")
	})
	public ResponseEntity<String> approveReserves(@RequestParam Long id, HttpServletRequest request) {
		// 세션 확인 코드 추가
		HttpSession session = request.getSession(false);
		if (session == null) { // 세션이 없으면 홈으로 이동
			return new ResponseEntity<>(null, HttpStatus.FOUND);
		}

		LoginDTO loginDTO =  (LoginDTO) session.getAttribute(PbSessionConst.LOGIN_PB);
		if (loginDTO == null) { // 세션에 회원 데이터가 없으면 홈으로 이동
			return new ResponseEntity<>(null, HttpStatus.FOUND);
		}

		try {
			pbReserveService.approveReserve(id);
			return ResponseEntity.ok("상담 요청이 승인되었습니다.");
		} catch (IllegalArgumentException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
	}

	@Tag(name = "전체 상담 일정 캘린더", description = "PB의 상담 일정 캘린더 API")
	@Operation(summary = "특정날짜 상담 일정", description = "특정날짜 상담 일정 조회")
	@Parameters({
		@Parameter(name = "date", description = "상담날짜", example = "2024-12-15")
	})
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "요청에 성공하였습니다."),
		@ApiResponse(responseCode = "400", description = "유효하지 않은 요청"),
		@ApiResponse(responseCode = "404", description = "특정 날짜 상담일정을 찾을 수 없음"),
		@ApiResponse(responseCode = "500", description = "이미 승인된 요청입니다")
	})
	@GetMapping(params = {"date", "pbId"})
	public ResponseEntity<List<ResponseReserveByDateDTO>> getReservesByDate(@RequestParam LocalDate date,
		@RequestParam Long pbId, HttpServletRequest request) {
		// 세션 확인 코드 추가
		HttpSession session = request.getSession(false);
		if (session == null) { // 세션이 없으면 홈으로 이동
			return new ResponseEntity<>(HttpStatus.FOUND);
		}

		LoginDTO loginDTO = (LoginDTO) session.getAttribute(PbSessionConst.LOGIN_PB);
		if (loginDTO == null) { // 세션에 회원 데이터가 없으면 홈으로 이동
			return new ResponseEntity<>(HttpStatus.FOUND);
		}

		List<ResponseReserveByDateDTO> reserves = pbReserveService.getReservesByDate(date, pbId);
		if (reserves == null || reserves.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND); // 날짜에 맞는 상담 일정이 없으면 404 반환
		}

		return new ResponseEntity<>(reserves, HttpStatus.OK); // 정상적인 경우 200 OK와 함께 반환
	}
}
