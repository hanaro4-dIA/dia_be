package com.dia.dia_be.service.pb.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.dia.dia_be.domain.Product;
import com.dia.dia_be.dto.pb.productDTO.ResponseProductDTO;
import com.dia.dia_be.repository.ProductRepository;
import com.dia.dia_be.service.pb.intf.PbProductService;

@Service
public class PbProductServiceImpl implements PbProductService {

	private final ProductRepository productRepository;

	public PbProductServiceImpl(ProductRepository productRepository){
		this.productRepository = productRepository;
	}

	@Override
	public List<ResponseProductDTO> getProducts(String tag) {
		List<Product> products = productRepository.findAllByName(tag);
		return products.stream().map(ResponseProductDTO::from).toList();
	}
}
