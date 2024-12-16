package com.dia.dia_be.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.dia.dia_be.service.pb.intf.JournalService;

@SpringBootTest
public class JournalControllerTest {

	@Autowired
	private JournalService JournalService;

	@Test
	@DisplayName("journal controller test - 상담 일지 리스트 조회")
	void getJournals(){

	}

	@Test
	@DisplayName("journal controller test - 특정 상담 일지 조회")
	void getJournal(){

	}
}
