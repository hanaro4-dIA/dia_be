package com.dia.dia_be.websocket;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {
	private JournalKeywordHandler journalKeywordHandler;

	// 생성자 주입
	public WebSocketConfig(JournalKeywordHandler journalKeywordHandler) {
		this.journalKeywordHandler = journalKeywordHandler;
	}

	@Override
	public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
		registry.addHandler(new PbAvailabilityHandler(), "/ws/availability").setAllowedOrigins("*");
		registry.addHandler(new RequestConsultationHandler(), "/ws/consultation")
			.setAllowedOrigins("*");
		registry.addHandler(journalKeywordHandler, "/ws/journalkeyword")
			.setAllowedOrigins("*");
	}
}
