package com.dia.dia_be.websocket;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
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
		sessions.removeIf(existingSession -> existingSession.getRemoteAddress().equals(session.getRemoteAddress()));
		sessions.add(session);
		System.out.println("WebSocket  journalkeyword  연결 성공: " + session.getId());//클라이언트 세션
	}

	public void getJournalKeyword(Long journalId) {
		ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();

		executorService.scheduleAtFixedRate(() -> {
			try {
				List<JournalKeyword> journalKeywordList = journalKeywordRepository.findALLByJournalId(journalId);

				if (!journalKeywordList.isEmpty()) {
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

					for (WebSocketSession session : sessions) {
						session.sendMessage(new TextMessage(objectMapper.writeValueAsString(responseDTO)));
					}

					//폴링중지
					executorService.shutdown();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}, 0, 2, TimeUnit.SECONDS); //2초 간격
	}

	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
		sessions.remove(session);
		System.out.println("WebSocket journalkeyword 연결 종료: " + session.getId());//세션 제거
	}

}