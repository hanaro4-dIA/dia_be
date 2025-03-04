package com.dia.dia_be.dto.pb.hashtagDTO;

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

	public static String stringFrom(Hashtag hashtag) {
		return hashtag.getName();
	}

	// DTO 객체 생성 - of 메서드
	public static ResponseHashtagDTO of(String name) {
		return ResponseHashtagDTO.builder()
			.name(name)
			.build();
	}
}
