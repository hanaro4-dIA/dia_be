package com.dia.dia_be.dto.pb.profileDTO;

import java.util.List;
import java.util.stream.Collectors;

import com.dia.dia_be.domain.Pb;
import com.dia.dia_be.dto.pb.hashtagDTO.ResponseHashtagDTO;

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
public class ResponseProfileDTO {
	private Long pbId;
	private String name;
	private String office;
	private boolean availability;
	private List<ResponseHashtagDTO> HashtagList;
	private String introduce;

	// Entity -> DTO 변환
	public static ResponseProfileDTO from(Pb pb) {
		return ResponseProfileDTO.builder()
			.pbId(pb.getId())
			.name(pb.getName())
			.office(pb.getOffice())
			.availability(pb.isAvailability())
			.HashtagList(pb.getHashtag() != null
				? pb.getHashtag().stream()
				.map(ResponseHashtagDTO::from)
				.collect(Collectors.toList())
				: null)
			.introduce(pb.getIntroduce())
			.build();
	}

	// DTO 객체 생성 - of 메서드
	public static ResponseProfileDTO of(Long pbId, String name, String office, boolean availability,
		List<ResponseHashtagDTO> HashtagList, String introduce) {
		return ResponseProfileDTO.builder()
			.pbId(pbId)
			.name(name)
			.office(office)
			.availability(availability)
			.HashtagList(HashtagList)
			.introduce(introduce)
			.build();
	}

}
