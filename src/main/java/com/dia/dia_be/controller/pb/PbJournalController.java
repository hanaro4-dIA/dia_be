package com.dia.dia_be.controller.pb;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dia.dia_be.dto.pb.journalDTO.RequestJournalDTO;
import com.dia.dia_be.service.pb.intf.PbJournalService;
import com.dia.dia_be.service.pb.intf.PbReserveService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/pb/journals")
public class PbJournalController {

	private final PbJournalService pbJournalService;
	private final PbReserveService pbReserveService;

	public PbJournalController(PbJournalService pbJournalService, PbReserveService pbReserveService) {
		this.pbJournalService = pbJournalService;
		this.pbReserveService = pbReserveService;
	}

	@GetMapping()
	@Tag(name = "상담 일지 가져오기", description = "상담 일지 조회 API")
	@Operation(summary = "상담 일지 리스트 조회")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "요청에 성공하였습니다.",
			content = @Content(mediaType = "application/json")),
		@ApiResponse(responseCode = "404", description = "상담 일지 조회에 실패했습니다.")
	})
	public ResponseEntity<?> getJournals() {
		try {
			return ResponseEntity.ok(pbJournalService.getJournals());
		} catch (Exception e) {
			return ResponseEntity.status(500).body(e.getMessage());
		}
	}

	@GetMapping("/{id}")
	@Tag(name = "상담 일지 가져오기", description = "상담 일지 조회 API")
	@Operation(summary = "ID 기반 특정 상담 일지 리스트 조회")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "요청에 성공하였습니다.",
			content = @Content(mediaType = "application/json")),
		@ApiResponse(responseCode = "404", description = "특정 상담 일지 조회에 실패했습니다.")
	})
	public ResponseEntity<?> getJournal(@PathVariable("id") Long id) {
		try {
			return ResponseEntity.ok(pbJournalService.getJournal(id));
		} catch (Exception e) {
			return ResponseEntity.status(500).body(e.getMessage());
		}
	}

	@GetMapping("/reserves/{reserve_id}/content")
	@Tag(name = "상담 일지 가져오기", description = "상담 일지 조회 API")
	@Operation(summary = "상담 일지 내 요청 상담 내용 상세 조회")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "요청에 성공하였습니다.",
			content = @Content(mediaType = "application/json")),
		@ApiResponse(responseCode = "404", description = "요청 상담 내용 상세 조회에 실패했습니다.")
	})
	public ResponseEntity<?> getConsultingContent(@PathVariable("reserve_id") Long id) {
		try {
			return ResponseEntity.ok(pbReserveService.getContent(id));
		} catch (Exception e) {
			return ResponseEntity.status(500).body(e.getMessage());
		}
	}

	@PostMapping()
	@Tag(name = "상담 일지 저장하기", description = "상담 일지 임시 저장 API")
	@Operation(summary = "상담 일지 임시 저장")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "요청에 성공하였습니다.",
			content = @Content(mediaType = "application/json")),
		@ApiResponse(responseCode = "404", description = "요청에 실패했습니다.")
	})
	public void saveJournal(@RequestBody RequestJournalDTO body){
		pbJournalService.addJournal(body);
	}

	@PostMapping("/transfer")
	@Tag(name = "상담 일지 저장하기", description = "상담 일지 전송 API")
	@Operation(summary = "상담 일지 저장 및 전송 상태 변경")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "요청에 성공하였습니다.",
			content = @Content(mediaType = "application/json")),
		@ApiResponse(responseCode = "404", description = "요청에 실패했습니다.")
	})
	public void transferJournal(@RequestBody RequestJournalDTO body){
			pbJournalService.addJournalAndChangeStatusComplete(body);
	}

}
