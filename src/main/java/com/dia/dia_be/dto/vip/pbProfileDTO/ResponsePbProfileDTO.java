package com.dia.dia_be.dto.vip.pbProfileDTO;

import java.time.LocalDate;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ResponsePbProfileDTO {
	private String name;
	private String introduction;
	private LocalDate date;
	private String location;
	private String tel;
	private String career;
	private String imageUrl;
	private List<String> tags;
	private boolean isOnline;
}
