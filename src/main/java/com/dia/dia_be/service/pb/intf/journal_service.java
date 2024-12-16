package com.dia.dia_be.service.pb.intf;

import java.util.List;

import com.dia.dia_be.dto.pb.journal_dto.response_journal_dto;

public interface journal_service {

	public List<response_journal_dto> getJournals();

	public response_journal_dto getJournal(Long id);

}
