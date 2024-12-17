package com.dia.dia_be.service.vip.intf;

import java.util.List;


public interface VipJournalService {

	public List<ResponseSimpleJournalDTO> getSimpleJournals(Long CustomerId);

	public ResponseJournalDTO getJournal(Long customerId, Long journalId);

	public List<ResponseJournalScriptDTO> getJournalScripts(Long journalId);
}
