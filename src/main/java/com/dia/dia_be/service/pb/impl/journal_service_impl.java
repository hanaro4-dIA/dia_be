package com.dia.dia_be.service.pb.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.dia.dia_be.dto.pb.journal_dto.response_journal_dto;
import com.dia.dia_be.repository.Journal_repository;
import com.dia.dia_be.service.pb.intf.journal_service;

@Service
public class journal_service_impl implements journal_service {

	private final Journal_repository journal_repository;

	public journal_service_impl(Journal_repository journal_repository){
		this.journal_repository = journal_repository;
	}

	@Override
	public List<response_journal_dto> getJournals() {
		return journal_repository.findAll().stream().map(response_journal_dto::from).toList();

	}

	@Override
	public response_journal_dto getJournal(Long id) {
		return response_journal_dto.from(journal_repository.findById(id).orElseThrow());
	}

}
