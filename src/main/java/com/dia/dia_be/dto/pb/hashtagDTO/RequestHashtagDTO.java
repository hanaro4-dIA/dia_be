package com.dia.dia_be.dto.pb.hashtagDTO;

import com.dia.dia_be.domain.Hashtag;
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
public class RequestHashtagDTO {
	private Long id;
	private Long pbId;
	private String name;

	public Hashtag toEntity(Pb pb) {
		return Hashtag.builder()
			.pb(pb)
			.name(this.name)
			.build();

	}

	// DTO 객체 생성 - of 메서드
	public static RequestHashtagDTO of(Long pbId, String name) {
		return RequestHashtagDTO.builder()
			.pbId(pbId)
			.name(name)
			.build();
	}
}
