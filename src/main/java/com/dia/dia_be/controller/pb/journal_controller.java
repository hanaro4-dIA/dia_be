package com.dia.dia_be.controller.pb;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dia.dia_be.dto.pb.journal_dto.response_journal_dto;
import com.dia.dia_be.service.pb.intf.journal_service;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/pb")
public class journal_controller {

	private final journal_service journal_service;

	public journal_controller(journal_service journal_service){
		this.journal_service = journal_service;
	}

	@GetMapping("/journals")
	@Tag(name = "상담 일지 가져오기", description = "상담 일지 조회 API")
	@Operation(summary = "상담 일지 리스트 조회")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "요청에 성공하였습니다.",
			content = @Content(mediaType = "application/json")),
		@ApiResponse(responseCode = "404", description = "상담 일지 조회에 실패했습니다.")
	})
	public List<response_journal_dto> getJournals(){
		return journal_service.getJournals();
	}

	@GetMapping("/journals/{id}")
	@Tag(name = "상담 일지 가져오기", description = "상담 일지 조회 API")
	@Operation(summary = "ID 기반 특정 상담 일지 리스트 조회")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "요청에 성공하였습니다.",
			content = @Content(mediaType = "application/json")),
		@ApiResponse(responseCode = "404", description = "툭정 상담 일지 조회에 실패했습니다.")
	})
	public response_journal_dto getJournal(@PathVariable("id") Long id){
		return journal_service.getJournal(id);
	}

}
