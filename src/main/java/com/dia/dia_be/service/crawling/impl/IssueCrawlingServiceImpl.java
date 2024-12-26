package com.dia.dia_be.service.crawling.impl;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.dia.dia_be.domain.Issue;
import com.dia.dia_be.domain.JournalKeyword;
import com.dia.dia_be.dto.crawling.IssueDTO;
import com.dia.dia_be.repository.IssueRepository;
import com.dia.dia_be.repository.JournalKeywordRepository;
import com.dia.dia_be.service.crawling.intf.IssueCrawlingService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class IssueCrawlingServiceImpl implements IssueCrawlingService {

	private final WebDriver webDriver;
	private final JournalKeywordRepository journalKeywordRepository;
	private final IssueRepository issueRepository;
	private final String baseUrl = "https://www.mk.co.kr/search?word=";

	public IssueCrawlingServiceImpl(WebDriver webDriver,
		JournalKeywordRepository journalKeywordRepository,
		IssueRepository issueRepository) {
		this.webDriver = webDriver;
		this.journalKeywordRepository = journalKeywordRepository;
		this.issueRepository = issueRepository;
	}

	@Override
	public List<IssueDTO> issueCrawlingWithSingleKeyword(String keyword) {
		String searchUrl = baseUrl + keyword; // 키워드별 검색 URL 생성
		return issueCrawling(searchUrl); // 기존 크롤링 메서드 호출
	}

	@Override
	public List<IssueDTO> issueCrawling(String url) {
		List<IssueDTO> newsList = new ArrayList<>();

		try {
			// URL로 이동
			webDriver.get(url);

			// 로드 기다림
			WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(10));
			wait.until(ExpectedConditions.presenceOfElementLocated(By.id("list_area")));

			// 뉴스 리스트 영역
			WebElement listArea = webDriver.findElement(By.id("list_area"));
			List<WebElement> articles = listArea.findElements(By.cssSelector("li.news_node"));

			// 각 기사 처리
			for (WebElement article : articles) {
				IssueDTO dto = extractArticleData(article);
				if (dto != null) {
					newsList.add(dto);
				}
			}
		} catch (Exception e) {
			log.info("URL [" + url + "] 크롤링 중 오류 발생: " + e.getMessage());
		}

		return newsList;
	}

	@Override
	public IssueDTO extractArticleData(WebElement article) {
		try {
			IssueDTO dto = new IssueDTO();

			// 제목
			dto.setIssueTitle(extractTitle(article));

			//링크
			dto.setIssueUrl(extractLink(article));

			// 이미지
			dto.setImageUrl(extractImage(article));

			return dto;
		} catch (Exception e) {
			log.info("Error extracting article data: " + e.getMessage());
			return null;
		}
	}

	// 제목 추출
	@Override
	public String extractTitle(WebElement article) {
		try {
			WebElement titleElement = article.findElement(By.cssSelector("h3.news_ttl"));
			return titleElement.getText();
		} catch (NoSuchElementException e) {
			log.info("제목을 찾을 수 없습니다.");
			return null;
		}
	}

	// 링크 추출
	@Override
	public String extractLink(WebElement article) {
		try {
			WebElement titleElement = article.findElement(By.cssSelector("a.news_item"));
			return titleElement.getAttribute("href");
		} catch (NoSuchElementException e) {
			log.info("링크를 찾을 수 없습니다.");
			return null;
		}
	}

	// 이미지 추출
	@Override
	public String extractImage(WebElement article) {
		try {
			WebElement imgElement = article.findElement(By.cssSelector("div.thumb_area img"));
			return imgElement.getAttribute("data-src");
		} catch (NoSuchElementException e) {
			log.info("이미지가 없는 기사입니다.");
			return null;
		}
	}

	@Override
	public void saveIssue() {
		// DB에서 JournalKeyword 엔티티 가져오기
		List<JournalKeyword> journalKeywords = journalKeywordRepository.findAll();

		for (JournalKeyword journalKeyword : journalKeywords) {
			// JournalKeyword에서 검색 키워드 추출
			String keywordName = journalKeyword.getKeyword().getTitle();
			log.info("==================================");
			log.info("키워드 [\" + keywordName + \"]에 대한 결과:");
			log.info("==================================");

			// 각 키워드에 대해 크롤링 수행
			List<IssueDTO> newsList = issueCrawlingWithSingleKeyword(keywordName);

			if (newsList.isEmpty()) {
				log.info("크롤링 결과 없음: 키워드 [" + keywordName + "]");
			} else {
				newsList.forEach(news -> {
					try {
						// IssueDTO를 Issue 엔티티로 변환하여 저장
						Issue issue = news.toEntity(journalKeyword.getKeyword());
						issueRepository.save(issue);
						log.info("저장 완료 - 제목: " + issue.getIssueUrl());
					} catch (DataIntegrityViolationException e) {
						// 중복된 경우 예외 처리
						log.info("중복된 URL - 저장하지 않음: " + news.getIssueUrl());
					} catch (Exception e) {
						// 기타 예외 처리
						log.info("저장 실패: " + e.getMessage());
					}
				});
			}
		}
	}
}
