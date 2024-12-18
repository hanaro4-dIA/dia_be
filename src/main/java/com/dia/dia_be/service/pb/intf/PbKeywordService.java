package com.dia.dia_be.service.pb.intf;

import java.util.List;

import com.dia.dia_be.dto.pb.keywordDTO.ResponseKeywordDTO;

public interface PbKeywordService {

	public List<ResponseKeywordDTO> getKeywords();

	public ResponseKeywordDTO getKeyword(Long id);
}
