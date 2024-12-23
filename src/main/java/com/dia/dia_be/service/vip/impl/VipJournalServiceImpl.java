package com.dia.dia_be.service.vip.impl;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.StreamSupport;

import org.springframework.stereotype.Service;

import com.dia.dia_be.domain.Consulting;
import com.dia.dia_be.domain.Issue;
import com.dia.dia_be.domain.Product;
import com.dia.dia_be.domain.QConsulting;
import com.dia.dia_be.domain.QProduct;
import com.dia.dia_be.domain.QScript;
import com.dia.dia_be.dto.vip.journalDTO.ResponseJournalDTO;
import com.dia.dia_be.dto.vip.journalDTO.ResponseJournalScriptDTO;
import com.dia.dia_be.dto.vip.journalDTO.ResponseRecommendationDTO;
import com.dia.dia_be.dto.vip.journalDTO.ResponseSimpleJournalDTO;
import com.dia.dia_be.exception.GlobalException;
import com.dia.dia_be.exception.VipErrorCode;
import com.dia.dia_be.repository.ConsultingRepository;
import com.dia.dia_be.repository.IssueRepository;
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
	private final IssueRepository issueRepository;

	public VipJournalServiceImpl(ConsultingRepository consultingRepository,
		ProductRepository productRepository, ScriptRepository scriptRepository, IssueRepository issueRepository) {
		this.consultingRepository = consultingRepository;
		this.productRepository = productRepository;
		this.scriptRepository = scriptRepository;
		this.issueRepository = issueRepository;
	}

	@Override
	public List<ResponseSimpleJournalDTO> getSimpleJournals(Long customerId) {
		List<ResponseSimpleJournalDTO> journalsDTO = new ArrayList<>();
		Iterable<Consulting> consultingIterable = consultingRepository.findAll(
			qConsulting.customer.id.eq(customerId)
				.and(qConsulting.hopeDate.loe(LocalDate.now()))
				.and(qConsulting.approve.eq(true))
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
		return journalsDTO.stream()
			.sorted(Comparator.comparing(ResponseSimpleJournalDTO::getDate, Comparator.reverseOrder())
				.thenComparing(ResponseSimpleJournalDTO::getTime, Comparator.reverseOrder()))
			.toList();
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
		return new ResponseJournalDTO(journalId, consulting.getCategory().getName(),
			consulting.getHopeDate(),
			consulting.getHopeTime(), consulting.getTitle(), consulting.getCustomer().getPb().getName(),
			consulting.getJournal().getContents(),
			journalProducts);
	}

	@Override
	public List<ResponseJournalScriptDTO> getJournalScripts(Long journalId) {
		return StreamSupport.stream(scriptRepository.findAll(
				qScript.journal.id.eq(journalId)
			).spliterator(), false)
			.map(ResponseJournalScriptDTO::from)
			.sorted(Comparator.comparing(ResponseJournalScriptDTO::getSequence))
			.toList();
	}

	@Override
	public List<ResponseRecommendationDTO> getRecommendations(Long customerId) {
		List<Product> recommendedProducts = productRepository.findRecommendedProducts(customerId);
		List<Issue> recommendedIssues = issueRepository.findRecommendedIssues(customerId);

		/*
		 * 추천 상품의 개수가 4개 미만일 때 4개로 맞춰주는 코드.
		 * 그러나 상품 자체의 개수가 4개 미만일 시 상품의 개수로 맞춰짐.
		 * */
		if (recommendedProducts.size() < 4) {
			List<Long> productIds = recommendedProducts.stream().map(Product::getId).toList();
			List<Product> products = productRepository.findAll()
				.stream()
				.filter(product -> !productIds.contains(product.getId()))
				.toList();
			int i = 0;
			while (recommendedProducts.size() < 4 && products.size() != i) {
				recommendedProducts.add(products.get(i));
				i++;
			}

		}
		/*
		 * 추천 이슈의 개수가 4개 미만일 때 4개로 맞춰주는 코드.
		 * 그러나 이슈 자체의 개수가 4개 미만일 시 이슈의 개수로 맞춰짐.
		 * */
		if (recommendedIssues.size() < 4) {
			List<Long> issueIds = recommendedIssues.stream().map(Issue::getId).toList();
			List<Issue> issues = issueRepository.findAll()
				.stream()
				.filter(issue -> !issueIds.contains(issue.getId()))
				.toList();
			int i = 0;
			while (recommendedIssues.size() < 4 && issues.size() != i) {
				recommendedIssues.add(issues.get(i));
				i++;
			}
		}
		List<ResponseRecommendationDTO> recommendationDTOS = new ArrayList<>();
		recommendedProducts.stream().map(ResponseRecommendationDTO::from).forEach(recommendationDTOS::add);
		recommendedIssues.stream().map(ResponseRecommendationDTO::from).forEach(recommendationDTOS::add);
		return recommendationDTOS;
	}
}
