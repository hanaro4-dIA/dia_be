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
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/vip/journals")
public class VipJournalController {

	private final VipJournalService vipJournalService;
	private final SessionManager sessionManager;

	public VipJournalController(VipJournalService vipJournalService, SessionManager sessionManager) {
		this.vipJournalService = vipJournalService;
		this.sessionManager = sessionManager;
	}

	@GetMapping
	@Tag(name = "상담내역 가져오기", description = "상담이 완료된 상담 일지")
	@Operation(summary = "상담제목,카테고리,상담일,상담시,PB명")
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
	@Tag(name = "상담내역 가져오기", description = "상담이 완료된 상담 일지")
	@Operation(summary = "특정 상담일지")
	public ResponseEntity<?> findJournalById(@PathVariable("id") Long journalId, HttpServletRequest request) {
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
	@Tag(name = "상담내역 가져오기", description = "상담이 완료된 상담 일지")
	@Operation(summary = "상담 스크립트")
	public ResponseEntity<?> findScriptsByJournalId(@PathVariable("id") Long journalId, HttpServletRequest request) {
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
	@Tag(name = "상담내역 가져오기", description = "상담이 완료된 상담 일지")
	@Operation(summary = "맞춤 컨텐츠")
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
