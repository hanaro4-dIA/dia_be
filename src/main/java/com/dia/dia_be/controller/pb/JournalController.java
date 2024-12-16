package com.dia.dia_be.controller.pb;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dia.dia_be.dto.pb.journal_dto.ResponseJournalDTO;
import com.dia.dia_be.service.pb.intf.JournalService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/pb")
public class JournalController {

	private final JournalService journalService;

	public JournalController(JournalService journalService){
		this.journalService = journalService;
	}

	@GetMapping("/journals")
	@Tag(name = "상담 일지 가져오기", description = "상담 일지 조회 API")
	@Operation(summary = "상담 일지 리스트 조회")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "요청에 성공하였습니다.",
			content = @Content(mediaType = "application/json")),
		@ApiResponse(responseCode = "404", description = "상담 일지 조회에 실패했습니다.")
	})
	public List<ResponseJournalDTO> getJournals(){
		return journalService.getJournals();
	}

	@GetMapping("/journals/{id}")
	@Tag(name = "상담 일지 가져오기", description = "상담 일지 조회 API")
	@Operation(summary = "ID 기반 특정 상담 일지 리스트 조회")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "요청에 성공하였습니다.",
			content = @Content(mediaType = "application/json")),
		@ApiResponse(responseCode = "404", description = "툭정 상담 일지 조회에 실패했습니다.")
	})
	public ResponseJournalDTO getJournal(@PathVariable("id") Long id){
		return journalService.getJournal(id);
	}

}
