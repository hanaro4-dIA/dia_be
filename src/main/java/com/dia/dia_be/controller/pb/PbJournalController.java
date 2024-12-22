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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.dia.dia_be.domain.PbSessionConst;
import com.dia.dia_be.dto.pb.journalDTO.RequestJournalDTO;
import com.dia.dia_be.dto.pb.journalDTO.ScriptListResponseDTO;
import com.dia.dia_be.dto.pb.journalDTO.ScriptListWithKeywordsResponseDTO;
import com.dia.dia_be.dto.pb.loginDTO.LoginDTO;
import com.dia.dia_be.exception.GlobalException;
import com.dia.dia_be.service.pb.intf.PbJournalService;
import com.dia.dia_be.service.pb.intf.PbProductService;
import com.dia.dia_be.service.pb.intf.PbReserveService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/pb/journals")
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
	@Tag(name = "상담 일지 가져오기", description = "상담 일지 조회 API")
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
	@Tag(name = "상담 일지 가져오기", description = "상담 일지 조회 API")
	@Operation(summary = "ID 기반 특정 상담 일지 리스트 조회")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "요청에 성공하였습니다.",
			content = @Content(mediaType = "application/json")),
		@ApiResponse(responseCode = "500", description = "특정 상담 일지 조회에 실패했습니다.")
	})
	public ResponseEntity<?> getJournal(@PathVariable("id") Long id, HttpServletRequest request) {
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
	@Tag(name = "상담 일지 가져오기", description = "상담 일지 조회 API")
	@Operation(summary = "상담 일지 내 요청 상담 내용 상세 조회")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "요청에 성공하였습니다.",
			content = @Content(mediaType = "application/json")),
		@ApiResponse(responseCode = "500", description = "요청 상담 내용 상세 조회에 실패했습니다.")
	})
	public ResponseEntity<?> getConsultingContent(@PathVariable("reserve_id") Long id, HttpServletRequest request) {
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
	@Tag(name = "상담 일지 저장하기", description = "상담 일지 임시 저장 API")
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
	@Tag(name = "상담 일지 저장하기", description = "상담 일지 전송 API")
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
	@Tag(name = "상담 일지 작성 중 태그 기반 상품 검색", description = "상품 검색 API")
	@Operation(summary = "태그 기반 상품 검색")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "요청에 성공하였습니다.",
			content = @Content(mediaType = "application/json")),
		@ApiResponse(responseCode = "500", description = "요청에 실패했습니다.")
	})
	public ResponseEntity<?> getProducts(@RequestParam String tag, HttpServletRequest request) {
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

	@Tag(name = "임시 저장 상담 일지 조회", description = "임시 저장 상담 일지 조회 API")
	@Operation(summary = "임시 저장 상담 일지 조회")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "요청에 성공하였습니다.",
			content = @Content(mediaType = "application/json")),
		@ApiResponse(responseCode = "404", description = "요청에 실패했습니다.")
	})
	@GetMapping("/{id}/status")
	public ResponseEntity<?> getTemporarySavedJournal(@PathVariable("id") Long id, @RequestParam boolean complete,
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
	@Tag(name = "통화 녹음을 텍스트로 변환 및 키워드 추출", description = "stt and keyword API")
	@Operation(summary = "통화 녹음을 텍스트로 변환 및 키워드 추출")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "요청에 성공하였습니다.",
			content = @Content(mediaType = "application/json")),
		@ApiResponse(responseCode = "404", description = "요청에 실패했습니다.")
	})
	public ResponseEntity<ScriptListWithKeywordsResponseDTO> createScriptsAndKeyword(
		@PathVariable("journal_id") Long journal_id,
		@RequestParam("uploadFile") MultipartFile uploadFile, HttpServletRequest req) {

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

		return ResponseEntity.ok().body(pbJournalService.createScriptsAndKeyword(journal_id, filePath));
	}

	@GetMapping("/{journal_id}/scripts")
	@Tag(name = "저장된 script 가져오기", description = "상담일지 작성 화면에서 script 부분")
	@Operation(summary = "script 가져오기")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "요청에 성공하였습니다.",
			content = @Content(mediaType = "application/json")),
		@ApiResponse(responseCode = "404", description = "요청에 실패했습니다.")
	})
	public ResponseEntity<ScriptListResponseDTO> getScripts(@PathVariable("journal_id") Long journal_id,
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
}
