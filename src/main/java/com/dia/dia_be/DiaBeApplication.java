package com.dia.dia_be;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class DiaBeApplication {

	public static void main(String[] args) {
		SpringApplication.run(DiaBeApplication.class, args);
	}

}
