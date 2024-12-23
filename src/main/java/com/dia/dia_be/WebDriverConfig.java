package com.dia.dia_be;

import java.io.File;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WebDriverConfig {
	@Bean
	public WebDriver webDriver() {
		File chromeDriverFile = new File("C:\\Users\\campus2H044\\Desktop\\chromedriver-win64\\chromedriver.exe");

		// ChromeDriverService 설정
		ChromeDriverService service = new ChromeDriverService.Builder()
			.usingDriverExecutable(chromeDriverFile)
			.usingAnyFreePort()
			.build();

		// ChromeOptions 설정
		ChromeOptions options = new ChromeOptions();
		options.addArguments("--headless"); // 필요시 브라우저 숨김
		options.addArguments("--no-sandbox");
		options.addArguments("--disable-dev-shm-usage");

		// WebDriver 반환
		return new ChromeDriver(service, options);
	}
}
