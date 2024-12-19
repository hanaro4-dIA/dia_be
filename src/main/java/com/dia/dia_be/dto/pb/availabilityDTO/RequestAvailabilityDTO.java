package com.dia.dia_be.dto.pb.availabilityDTO;

import com.dia.dia_be.domain.Pb;

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
public class RequestAvailabilityDTO {
	private Long pbId;
	private boolean availability;

	public static RequestAvailabilityDTO from(Pb pb) {
		return RequestAvailabilityDTO.builder()
			.pbId(pb.getId())
			.availability(pb.isAvailability())
			.build();
	}

}
