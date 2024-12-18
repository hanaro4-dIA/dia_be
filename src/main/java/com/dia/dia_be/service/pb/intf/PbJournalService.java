package com.dia.dia_be.service.pb.intf;

import java.util.List;

import com.dia.dia_be.dto.pb.journalDTO.RequestJournalDTO;
import com.dia.dia_be.dto.pb.journalDTO.ResponseJournalDTO;
import com.dia.dia_be.dto.pb.journalDTO.ScriptListResponseDTO;
import com.dia.dia_be.dto.pb.journalDTO.ScriptListWithKeywordsResponseDTO;

public interface PbJournalService {

	public List<ResponseJournalDTO> getJournals();

	public ResponseJournalDTO getJournal(Long id);

	public void addJournal(RequestJournalDTO body);

	public void addJournalAndChangeStatusComplete(RequestJournalDTO body);


	ScriptListWithKeywordsResponseDTO createScriptsAndKeyword(Long journalId, String filePath);

	ScriptListResponseDTO getScripts(Long journalId);
}
