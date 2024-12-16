package com.dia.dia_be.controller.pb;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dia.dia_be.dto.pb.journal_dto.response_journal_dto;
import com.dia.dia_be.service.pb.intf.journal_service;

@RestController
@RequestMapping("/pb")
public class journal_controller {

	private final journal_service journal_service;

	public journal_controller(journal_service journal_service){
		this.journal_service = journal_service;
	}

	@GetMapping("/journals")
	public List<response_journal_dto> getJournals(){
		return journal_service.getJournals();
	}

	@GetMapping("/journals/{id}")
	public response_journal_dto getJournal(@PathVariable("id") Long id){
		return journal_service.getJournal(id);
	}

}
