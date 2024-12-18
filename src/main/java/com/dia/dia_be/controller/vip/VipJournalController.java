package com.dia.dia_be.controller.vip;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dia.dia_be.exception.GlobalException;
import com.dia.dia_be.service.vip.intf.VipJournalService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/vip/journals")
public class VipJournalController {

	private final VipJournalService vipJournalService;

	public VipJournalController(VipJournalService vipJournalService) {
		this.vipJournalService = vipJournalService;
	}

	@GetMapping
	@Tag(name = "상담내역 가져오기", description = "상담이 완료된 상담 일지")
	@Operation(summary = "상담제목,카테고리,상담일,상담시,PB명")
	public ResponseEntity<?> findAll() {
		final Long customerId = 1L;
		try {
			return ResponseEntity.ok(vipJournalService.getSimpleJournals(customerId));
		} catch (Exception e) {
			return ResponseEntity.status(500).body(e.getMessage());
		}
	}

	@GetMapping("/{id}")
	@Tag(name = "상담내역 가져오기", description = "상담이 완료된 상담 일지")
	@Operation(summary = "특정 상담일지")
	public ResponseEntity<?> findJournalById(@PathVariable("id") Long journalId) {
		final Long customerId = 1L;
		try {
			return ResponseEntity.ok(vipJournalService.getJournal(customerId, journalId));
		} catch (GlobalException e) {
			return ResponseEntity.status(404).body(e.getMessage());
		} catch (Exception e) {
			return ResponseEntity.status(500).body(e.getMessage());
		}
	}

	@GetMapping("/{id}/scripts")
	@Tag(name = "상담내역 가져오기", description = "상담이 완료된 상담 일지")
	@Operation(summary = "상담 스크립트")
	public ResponseEntity<?> findScriptsByJournalId(@PathVariable("id") Long journalId) {
		try {
			return ResponseEntity.ok(vipJournalService.getJournalScripts(journalId));
		} catch (Exception e) {
			return ResponseEntity.status(500).body(e.getMessage());
		}
	}

	@GetMapping("/recommendations")
	@Tag(name = "상담내역 가져오기", description = "상담이 완료된 상담 일지")
	@Operation(summary = "맞춤 컨텐츠")
	public ResponseEntity<?> getRecommendations() {
		final Long customerId = 1L;
		try {
			return ResponseEntity.ok(vipJournalService.getRecommendations(customerId));
		} catch (Exception e) {
			return ResponseEntity.status(500).body(e.getMessage());
		}
	}
}
