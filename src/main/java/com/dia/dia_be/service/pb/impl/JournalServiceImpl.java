package com.dia.dia_be.service.pb.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.dia.dia_be.dto.pb.journal_dto.ResponseJournalDTO;
import com.dia.dia_be.repository.JournalRepository;
import com.dia.dia_be.service.pb.intf.JournalService;

@Service
public class JournalServiceImpl implements JournalService {

	private final JournalRepository journalRepository;

	public JournalServiceImpl(JournalRepository journalRepository){
		this.journalRepository = journalRepository;
	}

	@Override
	public List<ResponseJournalDTO> getJournals() {
		return journalRepository.findAll().stream().map(ResponseJournalDTO::from).toList();

	}

	@Override
	public ResponseJournalDTO getJournal(Long id) {
		return ResponseJournalDTO.from(journalRepository.findById(id).orElseThrow());
	}

}
