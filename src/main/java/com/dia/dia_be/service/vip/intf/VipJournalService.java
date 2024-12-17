package com.dia.dia_be.service.vip.intf;

import java.util.List;

import com.dia.dia_be.dto.vip.ResponseJournalDTO;
import com.dia.dia_be.dto.vip.ResponseSimpleJournalDTO;

public interface VipJournalService {

	public List<ResponseSimpleJournalDTO> getSimpleJournals(Long CustomerId);

	public ResponseJournalDTO getJournal(Long customerId, Long journalId);
}
