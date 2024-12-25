package com.dia.dia_be.dto.pb.hashtagDTO;

import com.dia.dia_be.domain.Hashtag;
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
public class RequestHashtagDTO {
	@Schema(description = "해시태그명", example = "해시")
	private String name;

	public static RequestHashtagDTO of(String name) {
		return RequestHashtagDTO.builder()
			.name(name)
			.build();
	}

	public static RequestHashtagDTO from(Hashtag hashtag) { // Hashtag은 실제 도메인 객체로 가정
		return RequestHashtagDTO.builder()
			.name(hashtag.getName())
			.build();
	}

	public static Hashtag toEntity(Pb pb, String name) {
		return Hashtag.create(pb, name);
	}
}
