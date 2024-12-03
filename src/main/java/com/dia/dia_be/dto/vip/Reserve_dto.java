package com.dia.dia_be.dto.vip;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Builder
public class Reserve_dto {
	private Long id;
	private Long customerPbId;
	private Long categoryId;
	private Long consultingId;
	private String title;
	private String consultDate;
	private String consultTime;
	private String reserveDate;
	private String content;
}
