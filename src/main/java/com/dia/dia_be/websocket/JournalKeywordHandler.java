package com.dia.dia_be.websocket;

import java.util.concurrent.CopyOnWriteArrayList;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import com.dia.dia_be.dto.pb.availabilityDTO.RequestAvailabilityDTO;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class JournalKeywordHandler extends TextWebSocketHandler {
	private static final CopyOnWriteArrayList<WebSocketSession> sessions = new CopyOnWriteArrayList<>();
	private final ObjectMapper objectMapper = new ObjectMapper();

	@Override
	public void afterConnectionEstablished(WebSocketSession session) {
		sessions.add(session); // 클라이언트 세션 추가
	}

	public void getJournalKeyword(RequestAvailabilityDTO availabilityDTO) {
		sessions.forEach(session -> {
			try {
				session.sendMessage(new TextMessage(objectMapper.writeValueAsString(availabilityDTO)));
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