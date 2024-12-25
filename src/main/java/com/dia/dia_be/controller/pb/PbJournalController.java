package com.dia.dia_be.controller.pb;

import static com.dia.dia_be.exception.PbErrorCode.*;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.dia.dia_be.domain.PbSessionConst;
import com.dia.dia_be.dto.pb.journalDTO.RequestJournalDTO;
import com.dia.dia_be.dto.pb.journalDTO.ScriptListRequestDTO;
import com.dia.dia_be.dto.pb.journalDTO.ScriptListResponseDTO;
import com.dia.dia_be.dto.pb.journalDTO.ScriptListWithKeywordsResponseDTO;
import com.dia.dia_be.dto.pb.loginDTO.LoginDTO;
import com.dia.dia_be.exception.GlobalException;
import com.dia.dia_be.service.pb.intf.PbJournalService;
import com.dia.dia_be.service.pb.intf.PbProductService;
import com.dia.dia_be.service.pb.intf.PbReserveService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/pb/journals")
@Tag(name = "PB - 상담 일지", description = "상담 일지 관련 API")
public class PbJournalController {

	private final PbJournalService pbJournalService;
	private final PbReserveService pbReserveService;
	private final PbProductService pbProductService;

	public PbJournalController(PbJournalService pbJournalService, PbReserveService pbReserveService,
		PbProductService pbProductService) {
		this.pbJournalService = pbJournalService;
		this.pbReserveService = pbReserveService;
		this.pbProductService = pbProductService;
	}

	@GetMapping()
	@Operation(summary = "상담 일지 리스트 조회")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "요청에 성공하였습니다.",
			content = @Content(mediaType = "application/json")),
		@ApiResponse(responseCode = "500", description = "상담 일지 조회에 실패했습니다.")
	})
	public ResponseEntity<?> getJournals(HttpServletRequest request) {
		// 세션 확인 코드 추가
		HttpSession session = request.getSession(false);
		if (session == null) { // 세션이 없으면 홈으로 이동
			return new ResponseEntity<>(null, HttpStatus.FOUND);
		}

		LoginDTO loginDTO = (LoginDTO)session.getAttribute(PbSessionConst.LOGIN_PB);
		if (loginDTO == null) { // 세션에 회원 데이터가 없으면 홈으로 이동
			return new ResponseEntity<>(null, HttpStatus.FOUND);
		}

		try {
			return ResponseEntity.ok(pbJournalService.getJournals());
		} catch (Exception e) {
			return ResponseEntity.status(500).body(e.getMessage());
		}
	}

	@GetMapping("/{id}")
	@Operation(summary = "ID 기반 특정 상담 일지 리스트 조회")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "요청에 성공하였습니다.",
			content = @Content(mediaType = "application/json")),
		@ApiResponse(responseCode = "500", description = "특정 상담 일지 조회에 실패했습니다.")
	})
	public ResponseEntity<?> getJournal(
		@Parameter(description = "상담일지ID", required = true, example = "1") @PathVariable("id") Long id,
		HttpServletRequest request) {
		// 세션 확인 코드 추가
		HttpSession session = request.getSession(false);
		if (session == null) { // 세션이 없으면 홈으로 이동
			return new ResponseEntity<>(null, HttpStatus.FOUND);
		}

		LoginDTO loginDTO = (LoginDTO)session.getAttribute(PbSessionConst.LOGIN_PB);
		if (loginDTO == null) { // 세션에 회원 데이터가 없으면 홈으로 이동
			return new ResponseEntity<>(null, HttpStatus.FOUND);
		}

		try {
			return ResponseEntity.ok(pbJournalService.getJournal(id));
		} catch (Exception e) {
			return ResponseEntity.status(500).body(e.getMessage());
		}
	}

	@GetMapping("/reserves/{reserve_id}/content")
	@Operation(summary = "상담 일지 내 요청 상담 내용 상세 조회")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "요청에 성공하였습니다.",
			content = @Content(mediaType = "application/json")),
		@ApiResponse(responseCode = "500", description = "요청 상담 내용 상세 조회에 실패했습니다.")
	})
	public ResponseEntity<?> getConsultingContent(
		@Parameter(description = "상담일지ID", required = true, example = "1") @PathVariable("reserve_id") Long id,
		HttpServletRequest request) {
		// 세션 확인 코드 추가
		HttpSession session = request.getSession(false);
		if (session == null) { // 세션이 없으면 홈으로 이동
			return new ResponseEntity<>(null, HttpStatus.FOUND);
		}

		LoginDTO loginDTO = (LoginDTO)session.getAttribute(PbSessionConst.LOGIN_PB);
		if (loginDTO == null) { // 세션에 회원 데이터가 없으면 홈으로 이동
			return new ResponseEntity<>(null, HttpStatus.FOUND);
		}

		try {
			return ResponseEntity.ok(pbReserveService.getContent(id));
		} catch (Exception e) {
			return ResponseEntity.status(500).body(e.getMessage());
		}
	}

	@PostMapping()
	@Operation(summary = "상담 일지 임시 저장")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "요청에 성공하였습니다.",
			content = @Content(mediaType = "application/json")),
		@ApiResponse(responseCode = "404", description = "요청에 실패했습니다.")
	})
	public ResponseEntity<Object> saveJournal(@RequestBody RequestJournalDTO body, HttpServletRequest request) {
		// 세션 확인 코드 추가
		HttpSession session = request.getSession(false);
		if (session == null) { // 세션이 없으면 홈으로 이동
			return new ResponseEntity<>(null, HttpStatus.FOUND);
		}

		LoginDTO loginDTO = (LoginDTO)session.getAttribute(PbSessionConst.LOGIN_PB);
		if (loginDTO == null) { // 세션에 회원 데이터가 없으면 홈으로 이동
			return new ResponseEntity<>(null, HttpStatus.FOUND);
		}

		pbJournalService.addJournal(body);
		return null;
	}

	@PostMapping("/transfer")
	@Operation(summary = "상담 일지 저장 및 전송 상태 변경")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "요청에 성공하였습니다.",
			content = @Content(mediaType = "application/json")),
		@ApiResponse(responseCode = "404", description = "요청에 실패했습니다.")
	})
	public ResponseEntity<Object> transferJournal(@RequestBody RequestJournalDTO body, HttpServletRequest request) {
		// 세션 확인 코드 추가
		HttpSession session = request.getSession(false);
		if (session == null) { // 세션이 없으면 홈으로 이동
			return new ResponseEntity<>(null, HttpStatus.FOUND);
		}

		LoginDTO loginDTO = (LoginDTO)session.getAttribute(PbSessionConst.LOGIN_PB);
		if (loginDTO == null) { // 세션에 회원 데이터가 없으면 홈으로 이동
			return new ResponseEntity<>(null, HttpStatus.FOUND);
		}
		pbJournalService.addJournalAndChangeStatusComplete(body);
		return null;
	}

	@GetMapping("/products")
	@Operation(summary = "태그 기반 상품 검색")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "요청에 성공하였습니다.",
			content = @Content(mediaType = "application/json")),
		@ApiResponse(responseCode = "500", description = "요청에 실패했습니다.")
	})
	public ResponseEntity<?> getProducts(
		@Parameter(description = "태그명", required = true, example = "예금") @RequestParam String tag,
		HttpServletRequest request) {
		// 세션 확인 코드 추가
		HttpSession session = request.getSession(false);
		if (session == null) { // 세션이 없으면 홈으로 이동
			return new ResponseEntity<>(null, HttpStatus.FOUND);
		}

		LoginDTO loginDTO = (LoginDTO)session.getAttribute(PbSessionConst.LOGIN_PB);
		if (loginDTO == null) { // 세션에 회원 데이터가 없으면 홈으로 이동
			return new ResponseEntity<>(null, HttpStatus.FOUND);
		}

		try {
			return ResponseEntity.ok(pbProductService.getProducts(tag));
		} catch (Exception e) {
			return ResponseEntity.status(500).body(e.getMessage());
		}
	}

	@Operation(summary = "임시 저장 상담 일지 조회")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "요청에 성공하였습니다.",
			content = @Content(mediaType = "application/json")),
		@ApiResponse(responseCode = "404", description = "요청에 실패했습니다.")
	})
	@GetMapping("/{id}/status")
	public ResponseEntity<?> getTemporarySavedJournal(
		@Parameter(description = "상담일지ID", required = true, example = "1") @PathVariable("id") Long id,
		@RequestParam boolean complete,
		HttpServletRequest request) {
		// 세션 확인 코드 추가
		HttpSession session = request.getSession(false);
		if (session == null) { // 세션이 없으면 홈으로 이동
			return new ResponseEntity<>(null, HttpStatus.FOUND);
		}

		LoginDTO loginDTO = (LoginDTO)session.getAttribute(PbSessionConst.LOGIN_PB);
		if (loginDTO == null) { // 세션에 회원 데이터가 없으면 홈으로 이동
			return new ResponseEntity<>(null, HttpStatus.FOUND);
		}

		try {
			return ResponseEntity.ok(pbJournalService.getTemporarySavedJournal(id, complete));
		} catch (Exception e) {
			return ResponseEntity.status(404).body(e.getMessage());
		}
	}

	//keyword python server 구현 후 연결

	@PostMapping("{journal_id}/transcripts")
	@Operation(summary = "통화 녹음을 텍스트로 변환 및 키워드 추출")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "요청에 성공하였습니다.",
			content = @Content(mediaType = "application/json")),
		@ApiResponse(responseCode = "404", description = "요청에 실패했습니다.")
	})
	public ResponseEntity<ScriptListWithKeywordsResponseDTO> createScriptsAndKeyword(
		@Parameter(description = "상담일지ID", required = true, example = "1") @PathVariable("journal_id") Long journal_id,
		@RequestParam("uploadFile") MultipartFile uploadFile, HttpServletRequest req) {
		// 세션 확인 코드 추가
		HttpSession session = req.getSession(false);
		if (session == null) { // 세션이 없으면 홈으로 이동
			return new ResponseEntity<>(null, HttpStatus.FOUND);
		}

		LoginDTO loginDTO = (LoginDTO)session.getAttribute(PbSessionConst.LOGIN_PB);
		if (loginDTO == null) { // 세션에 회원 데이터가 없으면 홈으로 이동
			return new ResponseEntity<>(null, HttpStatus.FOUND);
		}

		String uploadPath = req.getServletContext().getRealPath("/upload");
		Path uploadPath_path = Paths.get(uploadPath);
		if (!Files.exists(uploadPath_path)) {
			try {
				Files.createDirectories(uploadPath_path); // 디렉토리가 없다면 생성
			} catch (IOException e) {
				throw new GlobalException(RECORD_SAVE_FAILED);
			}
		}
		String fileName = uploadFile.getOriginalFilename();
		String filePath = uploadPath + "/" + fileName;

		try {
			BufferedOutputStream os = new BufferedOutputStream(new FileOutputStream(filePath));
			os.write(uploadFile.getBytes());
			os.close();
		} catch (Exception e) {
			throw new GlobalException(RECORD_SAVE_FAILED);
		}

		return ResponseEntity.ok()
			.body(pbJournalService.createScriptsAndKeyword(loginDTO.getPbId(), journal_id, filePath));
	}

	@PutMapping("{journal_id}/transcripts")
	@Operation(summary = "스크립트 수정 및 키워드 재추출")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "요청에 성공하였습니다.",
			content = @Content(mediaType = "application/json")),
		@ApiResponse(responseCode = "404", description = "요청에 실패했습니다.")
	})
	public ResponseEntity<ScriptListWithKeywordsResponseDTO> editScriptsAndKeyword(
		@Parameter(description = "상담일지ID", required = true, example = "1") @PathVariable("journal_id") Long journal_id,
		@RequestBody ScriptListRequestDTO scriptListRequestDTO, HttpServletRequest request) {
		// 세션 확인 코드 추가
		HttpSession session = request.getSession(false);
		if (session == null) { // 세션이 없으면 홈으로 이동
			return new ResponseEntity<>(null, HttpStatus.FOUND);
		}

		LoginDTO loginDTO = (LoginDTO)session.getAttribute(PbSessionConst.LOGIN_PB);
		if (loginDTO == null) { // 세션에 회원 데이터가 없으면 홈으로 이동
			return new ResponseEntity<>(null, HttpStatus.FOUND);
		}
		return ResponseEntity.ok()
			.body(pbJournalService.editScriptsAndKeyword(loginDTO.getPbId(), journal_id, scriptListRequestDTO));
	}

	@GetMapping("/{journal_id}/scripts")
	@Operation(summary = "script 가져오기")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "요청에 성공하였습니다.",
			content = @Content(mediaType = "application/json")),
		@ApiResponse(responseCode = "404", description = "요청에 실패했습니다.")
	})
	public ResponseEntity<ScriptListResponseDTO> getScripts(
		@Parameter(description = "상담일지ID", required = true, example = "1") @PathVariable("journal_id") Long journal_id,
		HttpServletRequest request) {
		// 세션 확인 코드 추가
		HttpSession session = request.getSession(false);
		if (session == null) { // 세션이 없으면 홈으로 이동
			return new ResponseEntity<>(null, HttpStatus.FOUND);
		}

		LoginDTO loginDTO = (LoginDTO)session.getAttribute(PbSessionConst.LOGIN_PB);
		if (loginDTO == null) { // 세션에 회원 데이터가 없으면 홈으로 이동
			return new ResponseEntity<>(null, HttpStatus.FOUND);
		}

		return ResponseEntity.ok().body(pbJournalService.getScripts(journal_id));
	}

	@DeleteMapping("/{journalId}/script")
	@Operation(summary = "script 삭제")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "요청에 성공하였습니다.",
			content = @Content(mediaType = "application/json")),
		@ApiResponse(responseCode = "404", description = "요청에 실패했습니다.")
	})
	public ResponseEntity<?> deleteScript(
		@Parameter(description = "상담일지ID", required = true, example = "1") @PathVariable("journalId") Long journalId,
		@Parameter(description = "스크립트ID", required = true, example = "1") @RequestParam Long scriptId,
		@Parameter(description = "스크립트순서", required = true, example = "1") @RequestParam Long scriptSequence,
		HttpServletRequest request) {
		// 세션 확인 코드 추가
		HttpSession session = request.getSession(false);
		if (session == null) { // 세션이 없으면 홈으로 이동
			return new ResponseEntity<>(null, HttpStatus.FOUND);
		}

		LoginDTO loginDTO = (LoginDTO)session.getAttribute(PbSessionConst.LOGIN_PB);
		if (loginDTO == null) { // 세션에 회원 데이터가 없으면 홈으로 이동
			return new ResponseEntity<>(null, HttpStatus.FOUND);
		}

		try {
			pbJournalService.deleteScript(journalId, scriptId, scriptSequence);
			return ResponseEntity.ok().build();
		} catch (Exception e) {
			return ResponseEntity.status(400).body(e.getMessage());
		}
	}
}
