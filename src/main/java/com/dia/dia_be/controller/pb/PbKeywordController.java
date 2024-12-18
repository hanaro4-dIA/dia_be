package com.dia.dia_be.controller.pb;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dia.dia_be.service.pb.intf.PbKeywordService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/pb/keywords")
public class PbKeywordController {

	private final PbKeywordService keywordService;

	public PbKeywordController (PbKeywordService keywordService){
		this.keywordService = keywordService;
	}

	@GetMapping()
	@Tag(name = "키워드 목록 가져오기", description = "키워드 목록 조회 API")
	@Operation(summary = "키워드 리스트 조회")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "요청에 성공하였습니다.",
			content = @Content(mediaType = "application/json")),
		@ApiResponse(responseCode = "404", description = "상담 일지 조회에 실패했습니다.")
	})
	public ResponseEntity<?> getKeywords(){
		try{
			return ResponseEntity.ok(keywordService.getKeywords());
		}catch (Exception e){
			return ResponseEntity.status(500).body(e.getMessage());
		}
	}

	@GetMapping("/{id}")
	@Tag(name = "키워드 목록 가져오기", description = "특정 키워드 조회 API")
	@Operation(summary = "특정 키워드 조회")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "요청에 성공하였습니다.",
			content = @Content(mediaType = "application/json")),
		@ApiResponse(responseCode = "404", description = "상담 일지 조회에 실패했습니다.")
	})
	public ResponseEntity<?> getKeyword(@PathVariable("id") Long id){
		try{
			return ResponseEntity.ok(keywordService.getKeyword(id));
		}catch (Exception e){
			return ResponseEntity.status(500).body(e.getMessage());
		}
	}

}
