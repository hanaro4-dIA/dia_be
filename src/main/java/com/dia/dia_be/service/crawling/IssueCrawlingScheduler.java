package com.dia.dia_be.service.crawling;

import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.dia.dia_be.service.crawling.intf.IssueCrawlingService;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
@Profile("!local")
public class IssueCrawlingScheduler {

	private final IssueCrawlingService issueCrawlingService;

	public IssueCrawlingScheduler(IssueCrawlingService issueCrawlingService) {
		this.issueCrawlingService = issueCrawlingService;
	}

	@Transactional
	@Scheduled(cron = "0 35 6 27 * *") //매달 27일에 실행하도록
	public void scheduleCrawling() {
		log.info("크롤링 스케줄러 시작");
		try {
			issueCrawlingService.saveIssue();
			log.info("크롤링 및 저장 완료");
		} catch (Exception e) {
			log.info("크롤링 중 오류 발생: " + e.getMessage());
		}
	}
}
