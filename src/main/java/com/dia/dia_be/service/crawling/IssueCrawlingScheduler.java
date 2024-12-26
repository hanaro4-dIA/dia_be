package com.dia.dia_be.service.crawling;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.dia.dia_be.service.crawling.intf.IssueCrawlingService;

import jakarta.transaction.Transactional;

@Component
public class IssueCrawlingScheduler {

	private final IssueCrawlingService issueCrawlingService;

	public IssueCrawlingScheduler(IssueCrawlingService issueCrawlingService) {
		this.issueCrawlingService = issueCrawlingService;
	}

	@Transactional
	@Scheduled(cron = "0 25 15 * * *") // 매일 오전 6시에 실행
	public void scheduleCrawling() {
		System.out.println("크롤링 스케줄러 시작");
		try {
			issueCrawlingService.saveIssue();
			System.out.println("크롤링 및 저장 완료");
		} catch (Exception e) {
			System.err.println("크롤링 중 오류 발생: " + e.getMessage());
		}
	}
}
