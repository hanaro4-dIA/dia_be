package com.dia.dia_be;

/*@Configuration
public class WebDriverConfig {
	@Bean
	public WebDriver webDriver() {
		// Chromedriver 경로 (리눅스 환경에 맞게 수정)
		File chromeDriverFile = new File("/usr/local/bin/chromedriver");

		// ChromeDriverService 설정
		ChromeDriverService service = new ChromeDriverService.Builder()
			.usingDriverExecutable(chromeDriverFile)
			.usingAnyFreePort()
			.build();

		// ChromeOptions 설정
		ChromeOptions options = new ChromeOptions();
		options.addArguments("--headless"); // EC2는 GUI가 없으므로 headless 모드 필수
		options.addArguments("--no-sandbox");
		options.addArguments("--disable-dev-shm-usage");

		// WebDriver 반환
		return new ChromeDriver(service, options);
	}
}*/
