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
		sessions.add(session);
		System.out.println("WebSocket  journalkeyword  연결 성공: " + session.getId());// 클라이언트 세션 추가
	}

	public void getJournalKeyword(Long journalId) {
		sessions.forEach(session -> {
			try {
				// 로그 추가
				System.out.println("Received journalId: " + journalId);

				// 1. 데이터베이스에서 키워드 조회
				List<JournalKeyword> journalKeywordList = journalKeywordRepository.findALLByJournalId(journalId);

				// 2. 키워드 데이터가 비어 있을 경우 로그 출력
				if (journalKeywordList.isEmpty()) {
					System.out.println("No keywords found for journalId: " + journalId);
				}

				// 3. DTO 변환 및 전송
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

				ResponseListJournalKeywordDTO responseDTO = ResponseListJournalKeywordDTO.of(keywordDTOList);
				session.sendMessage(new TextMessage(objectMapper.writeValueAsString(responseDTO)));
			} catch (Exception e) {
				e.printStackTrace();
			}
		});

	}

	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
		sessions.remove(session);
		System.out.println("WebSocket journalkeyword 연결 종료: " + session.getId());// 세션 제거
	}

}