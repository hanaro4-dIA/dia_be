package com.dia.dia_be.dto.pb.profileDTO;

import java.util.List;

import com.dia.dia_be.dto.pb.hashtagDTO.RequestHashtagDTO;

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
public class RequestProfileDTO {
	private String imageUrl;
	private List<RequestHashtagDTO> hashTagList;
	private String introduce;

	public static RequestProfileDTO of(String imageUrl,
		List<RequestHashtagDTO> hashTagList, String introduce) {
		return RequestProfileDTO.builder()
			.imageUrl(imageUrl)
			.hashTagList(hashTagList)
			.introduce(introduce)
			.build();
	}

}
