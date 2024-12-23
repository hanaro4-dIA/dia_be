package com.dia.dia_be.websocket;

import java.util.concurrent.CopyOnWriteArrayList;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import com.dia.dia_be.dto.vip.reserveDTO.ReserveWebSocketDTO;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class RequestConsultationHandler extends TextWebSocketHandler {
	private static final CopyOnWriteArrayList<WebSocketSession> sessions = new CopyOnWriteArrayList<>();
	private final ObjectMapper objectMapper = new ObjectMapper();

	@Override
	public void afterConnectionEstablished(WebSocketSession session) {
		sessions.add(session);
	}

	public void requestConsultation(ReserveWebSocketDTO reserveDTO) {
		sessions.forEach(session -> {
			try {
				String message = objectMapper.writeValueAsString(reserveDTO);
				System.out.println("Sending WebSocket message: " + message); // 디버깅 로그
				session.sendMessage(new TextMessage(message));

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
