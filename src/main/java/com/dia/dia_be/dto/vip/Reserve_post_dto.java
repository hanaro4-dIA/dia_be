package com.dia.dia_be.dto.vip;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
@Builder
public class Reserve_post_dto {
	private String date;
	private String time;
	private Long categoryId;
	private String title;
	private String content;
}
