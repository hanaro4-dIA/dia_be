package com.dia.dia_be.dto.pb;

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
public class CustomerDTO {

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


	// 엔티티를 DTO로 변환하는 메서드
	public static CustomerDTO from(Customer customer) {
		return CustomerDTO.builder()
			.id(customer.getId())
			.pbId(customer.getPb().getId())
			.password(customer.getPassword())
			.date(customer.getDate())
			.count(customer.getCount())
			.memo(customer.getMemo())
			.email(customer.getEmail())
			.name(customer.getName())
			.tel(customer.getTel())
			.address(customer.getAddress())
			.build();
	}

	// 새로운 DTO를 생성하는 메서드
	public static CustomerDTO of(Long id, Long pbId, String password,LocalDate date, int count, String memo, String email, String name, String tel, String address) {
		return CustomerDTO.builder()
			.id(id)
			.pbId(pbId)
			.password(password)
			.date(date)
			.count(count)
			.memo(memo)
			.email(email)
			.name(name)
			.tel(tel)
			.address(address)
			.build();
	}

	// DTO를 엔티티로 변환하는 메서드
	public Customer toEntity() {
		return Customer.builder()
			.id(this.id)
			.date(this.date)
			.password(this.password)
			.count(this.count)
			.memo(this.memo)
			.email(this.email)
			.name(this.name)
			.tel(this.tel)
			.address(this.address)
			.build();
	}
}
