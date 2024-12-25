package com.dia.dia_be.dto.pb.customerDTO;

import com.dia.dia_be.domain.Customer;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RequestCustomerDTO {

	@Schema(description = "메모", example = "memo example")
	private String memo;

	// 엔티티로 변환하는 메서드
	public static Customer toEntity(RequestCustomerDTO customerRequestDTO, Customer customer) {
		customer.update(customerRequestDTO.getMemo());
		return customer;
	}

}
