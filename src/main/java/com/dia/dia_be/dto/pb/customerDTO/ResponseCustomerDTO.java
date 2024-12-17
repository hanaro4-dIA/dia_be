package com.dia.dia_be.dto.pb.customerDTO;

import java.time.LocalDate;

import com.dia.dia_be.domain.Customer;

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
public class ResponseCustomerDTO {

	private Long id;
	private Long pbId;
	private String name;
	private String email;
	private String password;
	private String tel;
	private String address;
	private LocalDate date;
	private int count;
	private String memo;

	public static ResponseCustomerDTO toDto(Customer customer) {
		return ResponseCustomerDTO.builder()
			.id(customer.getId())
			.pbId(customer.getPb().getId())
			.name(customer.getName())
			.email(customer.getEmail())
			.password(customer.getPassword())
			.tel(customer.getTel())
			.address(customer.getAddress())
			.date(customer.getDate())
			.count(customer.getCount())
			.memo(customer.getMemo())
			.build();
	}


}
