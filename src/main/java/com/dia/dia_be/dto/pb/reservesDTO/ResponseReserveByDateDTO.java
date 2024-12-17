package com.dia.dia_be.dto.pb.reservesDTO;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

import com.dia.dia_be.domain.Consulting;

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
public class ResponseReserveByDateDTO {
	private Long consultingId;
	private LocalDate consultingDate;
	private LocalTime consultingTime;
	private String category;
	private String vipName;
	private String consultingTitle;

	// Entity -> DTO 변환
	public static ResponseReserveByDateDTO from(Consulting consulting) {
		return ResponseReserveByDateDTO.builder()
			.consultingId(consulting.getId())
			.consultingDate(consulting.getHopeDate())
			.consultingTime(consulting.getHopeTime())
			.category(consulting.getCategory().getName())
			.vipName(consulting.getCustomer().getName())
			.consultingTitle(consulting.getTitle())
			.build();
	}

	// List<Entity> -> List<DTO> 변환 메서드
	public static List<ResponseReserveByDateDTO> fromList(List<Consulting> consultings) {
		return consultings.stream()
			.map(ResponseReserveByDateDTO::from)
			.collect(Collectors.toList());
	}
}

