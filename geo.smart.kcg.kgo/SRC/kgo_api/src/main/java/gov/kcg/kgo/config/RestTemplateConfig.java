package gov.kcg.kgo.config;

import java.time.Duration;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestTemplateConfig {

	@Bean("myDataRestTemplate")
	public RestTemplate myDataRestTemplate() {
		RestTemplateBuilder restTemplateBuilder = new RestTemplateBuilder();
		restTemplateBuilder.setReadTimeout(Duration.ofSeconds(30));
		restTemplateBuilder.setConnectTimeout(Duration.ofSeconds(30));

		return restTemplateBuilder.build();
	}

	@Bean("myAccountTemplate")
	public RestTemplate myAccountTemplate() {
		RestTemplateBuilder restTemplateBuilder = new RestTemplateBuilder();
		restTemplateBuilder.setReadTimeout(Duration.ofSeconds(30));
		restTemplateBuilder.setConnectTimeout(Duration.ofSeconds(30));
		return restTemplateBuilder.build();
	}

	@Bean("ttcRestTemplate")
	public RestTemplate ttcRestTemplate() {
		RestTemplateBuilder restTemplateBuilder = new RestTemplateBuilder();
		restTemplateBuilder.setReadTimeout(Duration.ofSeconds(30));
		restTemplateBuilder.setConnectTimeout(Duration.ofSeconds(30));
		return restTemplateBuilder.build();
	}

	//GEO add 20210814
	@Bean("kgoNoAuthRestTemplate")
	public RestTemplate kgoNoAuthRestTemplate() {
		RestTemplateBuilder restTemplateBuilder = new RestTemplateBuilder();
		restTemplateBuilder.setReadTimeout(Duration.ofSeconds(30));
		restTemplateBuilder.setConnectTimeout(Duration.ofSeconds(30));
		return restTemplateBuilder.build();
	}
}