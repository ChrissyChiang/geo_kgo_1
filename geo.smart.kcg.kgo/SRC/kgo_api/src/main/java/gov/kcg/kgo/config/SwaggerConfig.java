package gov.kcg.kgo.config;

import com.google.common.base.Predicates;
import io.swagger.annotations.ApiOperation;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import gov.kcg.kgo.util.SpringUtil;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Tag;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig implements WebMvcConfigurer {

	/** 高雄城市資料平台 API Controller. */
	private final static String KCG_CITY_EXT_CONTROLLER = "KcgCityExtController";
	private final static String GEO_KCG_CITY_EXT_CONTROLLER = "GeoKcgCityExtController";

	/**
	 * GEO 20210828 modify for multi api package
	 **/
	@Bean
	public Docket createRestApi() {
		return new Docket(DocumentationType.SWAGGER_2).apiInfo(apiInfo())
				.tags(new Tag(KCG_CITY_EXT_CONTROLLER, "高雄城市資料平台 API Controller"),new Tag(GEO_KCG_CITY_EXT_CONTROLLER, "高雄城市資料平台 API Controller 2"))
				.select()
				.apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
				.apis(Predicates.or(
						RequestHandlerSelectors.basePackage("gov.kcg.kgo.controller"),
						RequestHandlerSelectors.basePackage("gov.kcg.kgo.geocontroller")
				))
				.paths(PathSelectors.any())// paths(PathSelectors.regex("/.*"))
				.build();
	}

	private ApiInfo apiInfo() {
		String buildTime = SpringUtil.getProperty("build.timestamp");
		return new ApiInfoBuilder().title("高雄市政府 - 一次就GO APIs").description("相關說明").termsOfServiceUrl("https://www.pixnet.net/pcard/B0212066/").license("Apache 2.0")
				.licenseUrl("http://www.apache.org/licenses/LICENSE-2.0.html").version(buildTime).build();
	}

}