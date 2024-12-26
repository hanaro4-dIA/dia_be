package com.dia.dia_be.controller.vip;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dia.dia_be.domain.VipSessionConst;
import com.dia.dia_be.dto.vip.loginDTO.VipLoginDTO;
import com.dia.dia_be.global.session.SessionManager;
import com.dia.dia_be.service.vip.intf.VipJournalService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/vip/journals")
@Tag(name = "VIP - 상담 일지", description = "상담이 완료된 상담 일지")
public class VipJournalController {

	private final VipJournalService vipJournalService;
	private final SessionManager sessionManager;

	public VipJournalController(VipJournalService vipJournalService, SessionManager sessionManager) {
		this.vipJournalService = vipJournalService;
		this.sessionManager = sessionManager;
	}

	@GetMapping
	@Operation(summary = "상담제목,카테고리,상담일,상담시,PB명")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "일지 목록 반환 성공"),
		@ApiResponse(responseCode = "500", description = "잘못된 요청으로 인한 반환 실패")
	})
	public ResponseEntity<?> findAll(HttpServletRequest request) {
		HttpSession session = sessionManager.getSession(request);
		if (session == null) {
			return new ResponseEntity<>("No session found", HttpStatus.FOUND);
		}

		// 세션에서 VIP 로그인 정보 가져오기
		VipLoginDTO loginDTO = (VipLoginDTO)session.getAttribute(VipSessionConst.LOGIN_VIP);
		if (loginDTO == null) {
			return new ResponseEntity<>("Can't find user data in session", HttpStatus.FOUND);
		}

		try {
			return ResponseEntity.ok(vipJournalService.getSimpleJournals(loginDTO.getCustomerId()));
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}

	}

	@GetMapping("/{id}")
	@Operation(summary = "특정 상담일지")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "상담 일지 반환 성공"),
		@ApiResponse(responseCode = "404", description = "상담 일지를 찾을 수 없음"),
		@ApiResponse(responseCode = "500", description = "잘못된 요청으로 인한 반환 실패")
	})
	public ResponseEntity<?> findJournalById(
		@Parameter(description = "상담일지ID", required = true, example = "1") @PathVariable("id") Long journalId,
		HttpServletRequest request) {
		HttpSession session = sessionManager.getSession(request);
		if (session == null) {
			return new ResponseEntity<>("No session found", HttpStatus.FOUND);
		}

		// 세션에서 VIP 로그인 정보 가져오기
		VipLoginDTO loginDTO = (VipLoginDTO)session.getAttribute(VipSessionConst.LOGIN_VIP);
		if (loginDTO == null) {
			return new ResponseEntity<>("Can't find user data in session", HttpStatus.FOUND);
		}

		try {
			return ResponseEntity.ok(vipJournalService.getJournal(loginDTO.getCustomerId(), journalId));

		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}

	}

	@GetMapping("/{id}/scripts")
	@Operation(summary = "상담 스크립트")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "상담 일지 스크립트 반환 성공"),
		@ApiResponse(responseCode = "404", description = "상담 일지 스크립트를 찾을 수 없음"),
		@ApiResponse(responseCode = "500", description = "잘못된 요청으로 인한 반환 실패")
	})
	public ResponseEntity<?> findScriptsByJournalId(
		@Parameter(description = "상담일지ID", required = true, example = "1") @PathVariable("id") Long journalId,
		HttpServletRequest request) {
		HttpSession session = sessionManager.getSession(request);
		if (session == null) {
			return new ResponseEntity<>("No session found", HttpStatus.FOUND);
		}

		// 세션에서 VIP 로그인 정보 가져오기
		VipLoginDTO loginDTO = (VipLoginDTO)session.getAttribute(VipSessionConst.LOGIN_VIP);
		if (loginDTO == null) {
			return new ResponseEntity<>("Can't find user data in session", HttpStatus.FOUND);
		}

		try {
			return ResponseEntity.ok(vipJournalService.getJournalScripts(loginDTO.getCustomerId(), journalId));
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@GetMapping("/recommendations")
	@Operation(summary = "맞춤 컨텐츠")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "맞춤 컨텐츠 반환 성공"),
		@ApiResponse(responseCode = "404", description = "맞춤 컨텐츠를 찾을 수 없음"),
		@ApiResponse(responseCode = "500", description = "잘못된 요청으로 인한 반환 실패")
	})
	public ResponseEntity<?> getRecommendations(HttpServletRequest request) {
		HttpSession session = sessionManager.getSession(request);
		if (session == null) {
			return new ResponseEntity<>("No session found", HttpStatus.FOUND);
		}

		// 세션에서 VIP 로그인 정보 가져오기
		VipLoginDTO loginDTO = (VipLoginDTO)session.getAttribute(VipSessionConst.LOGIN_VIP);
		if (loginDTO == null) {
			return new ResponseEntity<>("Can't find user data in session", HttpStatus.FOUND);
		}

		try {
			return ResponseEntity.ok(vipJournalService.getRecommendations(loginDTO.getCustomerId()));
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}

	}
}
