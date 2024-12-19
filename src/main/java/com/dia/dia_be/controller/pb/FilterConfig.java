package com.dia.dia_be.controller.pb;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterConfig {

	@Bean
	public AutoLoginFilter autoLoginFilter() {
		return new AutoLoginFilter();
	}
}
