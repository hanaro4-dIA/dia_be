package com.dia.dia_be.dto.pb.availabilityDTO;

import com.dia.dia_be.domain.Pb;

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
public class RequestAvailabilityDTO {
	@Schema(description = "PB의 ID", example = "1")
	private Long pbId;
	@Schema(description = "상담 가능 여부", example = "true")
	private boolean availability;

	public static RequestAvailabilityDTO from(Pb pb) {
		return RequestAvailabilityDTO.builder()
			.pbId(pb.getId())
			.availability(pb.isAvailability())
			.build();
	}

}
