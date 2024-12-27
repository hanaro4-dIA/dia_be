package com.dia.dia_be.websocket;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import com.dia.dia_be.domain.JournalKeyword;
import com.dia.dia_be.repository.JournalKeywordRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class JournalKeywordHandler extends TextWebSocketHandler {
	private static final CopyOnWriteArrayList<WebSocketSession> sessions = new CopyOnWriteArrayList<>();
	private final ObjectMapper objectMapper = new ObjectMapper();
	private final JournalKeywordRepository journalKeywordRepository;

	public JournalKeywordHandler(JournalKeywordRepository journalKeywordRepository) {
		this.journalKeywordRepository = journalKeywordRepository;
	}

	@Override
	public void afterConnectionEstablished(WebSocketSession session) {
		sessions.add(session); // 클라이언트 세션 추가
	}

	public void getJournalKeyword(Long journalId) {
		sessions.forEach(session -> {
			try {
				List<JournalKeyword> journalKeywordList = journalKeywordRepository.findALLByJournalId(journalId);
				//List<ResponseJournalKeywordDTO> journalKeywordList;
				session.sendMessage(new TextMessage(objectMapper.writeValueAsString(journalKeywordList)));
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