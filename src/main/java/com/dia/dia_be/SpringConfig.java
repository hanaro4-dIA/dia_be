package com.dia.dia_be;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class SpringConfig implements WebMvcConfigurer {

	@Override
	public void addCorsMappings (CorsRegistry registry){
		registry.addMapping("/**")
			.allowedOriginPatterns("*")  // 특정 도메인을 지정해도 됨
			.allowedHeaders("*")
			.allowedMethods("GET", "POST", "PUT", "DELETE", "HEAD", "OPTIONS", "PATCH")
			.exposedHeaders("Authorization", "RefreshToken", "Set-Cookie")  // 🔹 Set-Cookie 추가
			.allowCredentials(true);  // 쿠키 전송 허용
	}


	@Bean
	public OpenAPI openAPI() {
		return new OpenAPI()
			.components(new Components())
			.info(info());
	}

	private Info info() {
		return new Info()
			.version("0.1.0")
			.title("dIA API")
			.description("하나은행 디지털하나로 개발4기 1차 프로젝트 dIA팀 API");
	}
}
