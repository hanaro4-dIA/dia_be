package com.dia.dia_be.service.pb.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.dia.dia_be.domain.Keyword;
import com.dia.dia_be.dto.pb.keywordDTO.ResponseKeywordDTO;
import com.dia.dia_be.exception.CommonErrorCode;
import com.dia.dia_be.exception.GlobalException;
import com.dia.dia_be.repository.KeywordRepository;
import com.dia.dia_be.service.pb.intf.PbKeywordService;

@Service
public class PbKeywordServiceImpl implements PbKeywordService {

	private final KeywordRepository keywordRepository;

	public PbKeywordServiceImpl(KeywordRepository keywordRepository){
		this.keywordRepository = keywordRepository;
	}

	@Override
	public List<ResponseKeywordDTO> getKeywords() {
		List<Keyword> keywords = keywordRepository.findAll();
		return keywords.stream().map(ResponseKeywordDTO::from).toList();
	}

	@Override
	public ResponseKeywordDTO getKeyword(Long id) {
		Keyword keyword = keywordRepository.findById(id)
			.orElseThrow(() -> new GlobalException(CommonErrorCode.BAD_REQUEST));

		return ResponseKeywordDTO.from(keyword);
	}
}
