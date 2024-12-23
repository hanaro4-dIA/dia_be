package com.dia.dia_be.service.crawling.intf;

import java.util.List;

import org.openqa.selenium.WebElement;

import com.dia.dia_be.dto.crawling.IssueDTO;

public interface IssueCrawlingService {
	List<IssueDTO> issueCrawlingWithSingleKeyword(String keyword);

	List<IssueDTO> issueCrawling(String url);

	IssueDTO extractArticleData(WebElement article);

	// 제목 추출
	String extractTitle(WebElement article);

	// 링크 추출
	String extractLink(WebElement article);

	// 이미지 추출
	String extractImage(WebElement article);

	void saveIssue();

}
