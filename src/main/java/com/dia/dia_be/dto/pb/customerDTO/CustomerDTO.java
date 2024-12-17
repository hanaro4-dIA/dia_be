package com.dia.dia_be.dto.pb.customerDTO;

import java.time.LocalDate;

import com.dia.dia_be.domain.Customer;
import com.dia.dia_be.domain.Pb;
import com.dia.dia_be.exception.GlobalException;
import com.dia.dia_be.exception.PbErrorCode;
import com.dia.dia_be.repository.PbRepository;

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

	// 새로운 DTO를 생성하는 메서드
	public static CustomerDTO of(Long id, Long pbId, String password, LocalDate date, int count, String memo,
		String email, String name, String tel, String address) {
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
	public static Customer toEntity(CustomerDTO customerDTO, PbRepository pbRepository) {
		Pb pb = pbRepository.findById(customerDTO.getPbId())
			.orElseThrow(() -> new GlobalException(PbErrorCode.PROFILE_NOT_FOUND));

		return Customer.builder()
			.id(customerDTO.getId())
			.pb(pb)
			.password(customerDTO.getPassword())
			.date(customerDTO.getDate())
			.count(customerDTO.getCount())
			.memo(customerDTO.getMemo())
			.email(customerDTO.getEmail())
			.name(customerDTO.getName())
			.tel(customerDTO.getTel())
			.address(customerDTO.getAddress())
			.build();
	}

	// 엔티티를 DTO로 변환하는 메서드
	public static CustomerDTO toDto(Customer customer) {
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

}
