package com.dia.dia_be.service.pb.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.dia.dia_be.domain.Journal;
import com.dia.dia_be.domain.JournalProduct;
import com.dia.dia_be.domain.Product;
import com.dia.dia_be.dto.pb.journalDTO.RequestJournalDTO;
import com.dia.dia_be.dto.pb.journalDTO.ResponseJournalDTO;
import com.dia.dia_be.exception.GlobalException;
import com.dia.dia_be.exception.PbErrorCode;
import com.dia.dia_be.repository.ConsultingRepository;
import com.dia.dia_be.repository.JournalProductRepository;
import com.dia.dia_be.repository.JournalRepository;
import com.dia.dia_be.repository.ProductRepository;
import com.dia.dia_be.service.pb.intf.PbJournalService;

@Service
public class PbJournalServiceImpl implements PbJournalService {

	private final JournalRepository journalRepository;
	private final ConsultingRepository consultingRepository;
	private final JournalProductRepository journalProductRepository;
	private final ProductRepository productRepository;

	public PbJournalServiceImpl(JournalRepository journalRepository, ConsultingRepository consultingRepository, JournalProductRepository journalProductRepository, ProductRepository productRepository) {
		this.journalRepository = journalRepository;
		this.consultingRepository = consultingRepository;
		this.journalProductRepository = journalProductRepository;
		this.productRepository = productRepository;
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

	@Override
	public void addJournal(RequestJournalDTO body) {
		consultingRepository.findConsultingByIdAndUpdateTitleAndCategory(body.getConsultingId(),
			body.getConsultingTitle(), body.getCategoryId());

		journalRepository.updateContentsById(body.getConsultingId(), body.getJournalContents());
		List<Product> products = productRepository.findAllById(body.getRecommendedProductsKeys());
		Journal journal = journalRepository.findById(body.getConsultingId())
			.orElseThrow(() -> new GlobalException(PbErrorCode.JOURNAL_NOT_FOUND));
		for(Product product : products){
			JournalProduct journalProduct = JournalProduct.create(product, journal);
			journalProductRepository.save(journalProduct);
		}
	}

	@Override
	public void addJournalAndChangeStatusComplete(RequestJournalDTO body) {
		addJournal(body);
		journalRepository.updateCompleteStatusById(body.getConsultingId());
	}

}
