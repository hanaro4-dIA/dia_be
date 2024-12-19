package com.dia.dia_be.dto.vip.loginDTO;

import java.time.LocalDate;

import com.dia.dia_be.domain.Customer;
import com.dia.dia_be.domain.Journal;
import com.dia.dia_be.domain.Pb;
import com.dia.dia_be.dto.pb.journalDTO.ResponseTemporarySavedJournalDTO;
import com.dia.dia_be.dto.pb.productDTO.ResponseProductDTO;

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
public class RequestVipSignUpDTO {

	private String name;
	private String email;
	private String password;
	private String tel;
	private String address;

}
