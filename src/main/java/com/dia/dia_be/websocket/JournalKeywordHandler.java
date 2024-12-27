package com.dia.dia_be.websocket;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import com.dia.dia_be.domain.JournalKeyword;
import com.dia.dia_be.domain.Keyword;
import com.dia.dia_be.dto.pb.journalDTO.ResponseListJournalKeywordDTO;
import com.dia.dia_be.dto.pb.keywordDTO.ResponseKeywordDTO;
import com.dia.dia_be.repository.JournalKeywordRepository;
import com.dia.dia_be.repository.KeywordRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class JournalKeywordHandler extends TextWebSocketHandler {
	private static final CopyOnWriteArrayList<WebSocketSession> sessions = new CopyOnWriteArrayList<>();
	private final ObjectMapper objectMapper = new ObjectMapper();
	private final JournalKeywordRepository journalKeywordRepository;
	private final KeywordRepository keywordRepository;

	public JournalKeywordHandler(JournalKeywordRepository journalKeywordRepository,
		KeywordRepository keywordRepository) {
		this.journalKeywordRepository = journalKeywordRepository;
		this.keywordRepository = keywordRepository;
	}

	@Override
	public void afterConnectionEstablished(WebSocketSession session) {
		sessions.add(session); // 클라이언트 세션 추가
	}

	public void getJournalKeyword(Long journalId) {
		sessions.forEach(session -> {
			try {
				// 1. journalId를 기반으로 키워드 검색
				List<JournalKeyword> journalKeywordList = journalKeywordRepository.findALLByJournalId(journalId);

				// 2. 각 키워드의 추가 정보를 조회하여 DTO 리스트 생성
				List<ResponseKeywordDTO> keywordDTOList = journalKeywordList.stream()
					.map(journalKeyword -> {
						Keyword keyword = keywordRepository.findById(journalKeyword.getKeyword().getId())
							.orElseThrow(() -> new RuntimeException("Keyword not found"));
						return ResponseKeywordDTO.builder()
							.id(keyword.getId())
							.title(keyword.getTitle())
							.content(keyword.getContent())
							.build();
					})
					.collect(Collectors.toList());

				// 3. ResponseListJournalKeywordDTO 생성
				ResponseListJournalKeywordDTO responseDTO = ResponseListJournalKeywordDTO.of(keywordDTOList);

				// 4. 클라이언트로 데이터 전송
				session.sendMessage(new TextMessage(objectMapper.writeValueAsString(responseDTO)));
			} catch (Exception e) {
				e.printStackTrace();
			}
		});

	}

	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
		sessions.remove(session); // 세션 제거
	}

}