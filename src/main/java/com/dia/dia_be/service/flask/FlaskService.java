package com.dia.dia_be.service.flask;

import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import reactor.core.publisher.Mono;

@Service
public class FlaskService {
	private final WebClient webClient;

	public FlaskService(WebClient.Builder webClientBuilder) {
		this.webClient = webClientBuilder.baseUrl("http://localhost:5000").build();
	}

	public Mono<Void> sendTextToFlask(String text, Long journalId, Long customerId) {
		return webClient.post()
			.uri("/extract_keywords")
			.bodyValue(Map.of(
				"text", text,
				"journal_id", journalId,
				"customer_id", customerId
			))
			.retrieve()
			.bodyToMono(Void.class)
			.doOnSuccess(response -> {
				System.out.println("[INFO] Flask 서버로 전송 성공");
			})
			.doOnError(error -> {
				System.err.println("[ERROR] Flask 서버와의 통신 중 오류 발생: " + error.getMessage());
			});
	}
}
