package com.dia.dia_be.controller.vip;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dia.dia_be.dto.vip.journalDTO.ResponseJournalDTO;
import com.dia.dia_be.dto.vip.journalDTO.ResponseJournalScriptDTO;
import com.dia.dia_be.dto.vip.journalDTO.ResponseRecommendationDTO;
import com.dia.dia_be.dto.vip.journalDTO.ResponseSimpleJournalDTO;
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
	public ResponseEntity<List<ResponseSimpleJournalDTO>> findAll() {
		final Long customerId = 1L;

		return ResponseEntity.ok(vipJournalService.getSimpleJournals(customerId));
	}

	@GetMapping("/{id}")
	@Tag(name = "상담내역 가져오기", description = "상담이 완료된 상담 일지")
	@Operation(summary = "특정 상담일지")
	public ResponseEntity<ResponseJournalDTO> findJournalById(@PathVariable("id") Long journalId) {
		final Long customerId = 1L;

		return ResponseEntity.ok(vipJournalService.getJournal(customerId, journalId));
	}

	@GetMapping("/{id}/scripts")
	@Tag(name = "상담내역 가져오기", description = "상담이 완료된 상담 일지")
	@Operation(summary = "상담 스크립트")
	public ResponseEntity<List<ResponseJournalScriptDTO>> findScriptsByJournalId(@PathVariable("id") Long journalId) {
		return ResponseEntity.ok(vipJournalService.getJournalScripts(journalId));
	}

	@GetMapping("/recommendations")
	@Tag(name = "상담내역 가져오기", description = "상담이 완료된 상담 일지")
	@Operation(summary = "맞춤 컨텐츠")
	public ResponseEntity<List<ResponseRecommendationDTO>> getRecommendations() {
		final Long customerId = 1L;

		return ResponseEntity.ok(vipJournalService.getRecommendations(customerId));
	}
}
