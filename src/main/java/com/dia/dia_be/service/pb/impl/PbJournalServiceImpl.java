package com.dia.dia_be.service.pb.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.dia.dia_be.dto.pb.journalDTO.ResponseJournalDTO;
import com.dia.dia_be.exception.GlobalException;
import com.dia.dia_be.exception.PbErrorCode;
import com.dia.dia_be.repository.JournalRepository;
import com.dia.dia_be.service.pb.intf.PbJournalService;

@Service
public class PbJournalServiceImpl implements PbJournalService {

	private final JournalRepository journalRepository;

	public PbJournalServiceImpl(JournalRepository journalRepository) {
		this.journalRepository = journalRepository;
	}

	@Override
	public List<ResponseJournalDTO> getJournals() {
		return journalRepository.findAll().stream().map(ResponseJournalDTO::from).toList();

	}

	@Override
	public ResponseJournalDTO getJournal(Long id) {
		return ResponseJournalDTO.from(
			journalRepository.findById(id).orElseThrow(() -> new GlobalException(PbErrorCode.JOURNAL_NOT_FOUND)));
	}

}
