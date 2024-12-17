package com.dia.dia_be.service.vip.intf;

import java.util.List;

import com.dia.dia_be.dto.vip.journalDTO.ResponseJournalDTO;
import com.dia.dia_be.dto.vip.journalDTO.ResponseJournalScriptDTO;
import com.dia.dia_be.dto.vip.journalDTO.ResponseSimpleJournalDTO;

public interface VipJournalService {

	public List<ResponseSimpleJournalDTO> getSimpleJournals(Long CustomerId);

	public ResponseJournalDTO getJournal(Long customerId, Long journalId);

	public List<ResponseJournalScriptDTO> getJournalScripts(Long journalId);
}