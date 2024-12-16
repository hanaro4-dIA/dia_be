package com.dia.dia_be.dto.vip;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ResponseReserveInfoGetDTO {
	@Schema(description = "PB명", example = "안유진")
	private String pbName;
	@Schema(description = "고객명", example = "강재준")
	private String vipName;
}
