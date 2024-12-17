package com.dia.dia_be.service.vip.impl;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.StreamSupport;

import org.springframework.stereotype.Service;

import com.dia.dia_be.domain.Consulting;
import com.dia.dia_be.domain.Product;
import com.dia.dia_be.domain.QConsulting;
import com.dia.dia_be.domain.QProduct;
import com.dia.dia_be.domain.QScript;
import com.dia.dia_be.exception.GlobalException;
import com.dia.dia_be.exception.VipErrorCode;
import com.dia.dia_be.repository.ConsultingRepository;
import com.dia.dia_be.repository.ProductRepository;
import com.dia.dia_be.repository.ScriptRepository;
import com.dia.dia_be.service.vip.intf.VipJournalService;

@Service
public class VipJournalServiceImpl implements VipJournalService {
	private final ConsultingRepository consultingRepository;
	private final ProductRepository productRepository;
	private final ScriptRepository scriptRepository;
	private final QConsulting qConsulting = QConsulting.consulting;
	private final QProduct qProduct = QProduct.product;
	private final QScript qScript = QScript.script;

	public VipJournalServiceImpl(ConsultingRepository consultingRepository,
		ProductRepository productRepository, ScriptRepository scriptRepository) {
		this.consultingRepository = consultingRepository;
		this.productRepository = productRepository;
		this.scriptRepository = scriptRepository;
	}

	@Override
	public List<ResponseSimpleJournalDTO> getSimpleJournals(Long customerId) {
		List<ResponseSimpleJournalDTO> journalsDTO = new ArrayList<>();
		Iterable<Consulting> consultingIterable = consultingRepository.findAll(
			qConsulting.customer.id.eq(customerId).and(qConsulting.journal.complete.eq(true))
		);
		for (Consulting consulting : consultingIterable) {
			Long id = consulting.getJournal().getId();
			String category = consulting.getCategory().getName();
			String title = consulting.getTitle();
			LocalDate date = consulting.getHopeDate();
			LocalTime time = consulting.getHopeTime();
			String manager = consulting.getCustomer().getPb().getName();
			boolean status = consulting.getJournal().isComplete();
			ResponseSimpleJournalDTO journalDTO = new ResponseSimpleJournalDTO(
				id, category, title, date, time, manager, status
			);
			journalsDTO.add(journalDTO);
		}
		return journalsDTO;
	}

	@Override
	public ResponseJournalDTO getJournal(Long customerId, Long journalId) {
		Consulting consulting = consultingRepository.findOne(
			qConsulting.customer.id.eq(customerId).and(qConsulting.journal.id.eq(journalId))
		).orElseThrow(() -> new GlobalException(
			VipErrorCode.RESERVE_NOT_FOUND));
		List<Product> products = StreamSupport.stream(productRepository.findAll(
			qProduct.journalProduct.any().journal.id.eq(journalId)).spliterator(), false).toList();
		List<ResponseJournalDTO.Product> journalProducts = products.stream()
			.map(
				product -> new ResponseJournalDTO.Product(product.getId(), product.getName(), product.getProduct_url()))
			.toList();
		for (ResponseJournalDTO.Product journalProduct : journalProducts) {
			System.out.println(journalProduct.toString());
		}
		ResponseJournalDTO ret = new ResponseJournalDTO(journalId, consulting.getCategory().getName(),
			consulting.getHopeDate(),
			consulting.getHopeTime(), consulting.getCustomer().getPb().getName(), consulting.getJournal().getContents(),
			journalProducts);
		System.out.println(ret);
		return ret;
	}

	@Override
	public List<ResponseJournalScriptDTO> getJournalScripts(Long journalId) {
		return StreamSupport.stream(scriptRepository.findAll(
			qScript.journal.id.eq(journalId)
		).spliterator(), false).map(ResponseJournalScriptDTO::from).toList();
	}
}
