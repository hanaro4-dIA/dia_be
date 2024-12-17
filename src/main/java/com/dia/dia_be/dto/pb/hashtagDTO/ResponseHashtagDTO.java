package com.dia.dia_be.dto.pb.HashtagDTO;

import com.dia.dia_be.domain.Hashtag;

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
public class ResponseHashtagDTO {
	private String name;

	// Entity -> DTO 변환
	public static ResponseHashtagDTO from(Hashtag hashtag) {
		return ResponseHashtagDTO.builder()
			.name(hashtag.getName())
			.build();
	}

	// DTO 객체 생성 - of 메서드
	public static ResponseHashtagDTO of(String name) {
		return ResponseHashtagDTO.builder()
			.name(name)
			.build();
	}
}
