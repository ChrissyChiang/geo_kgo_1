package gov.kcg.kgo.security.config;
import java.util.Arrays;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;

import gov.kcg.kgo.repository.base.impl.BaseRepositoryImpl;

/** 20210901 GEO modify add another repository package **/

@Configuration
@EnableJpaRepositories(basePackages = {"gov.kcg.kgo.repository", "gov.kcg.kgo.georepository"}, repositoryBaseClass = BaseRepositoryImpl.class)
public class WebConf implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
            registry.addResourceHandler("swagger-ui.html").addResourceLocations("classpath:/META-INF/resources/");
            registry.addResourceHandler("swagger-ui.html/**").addResourceLocations("classpath:/META-INF/resources/**");
            registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
            registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
        	registry.addResourceHandler("/static/**").addResourceLocations("classpath:/WEB-INF/static/**");
        	registry.addResourceHandler("/kgo/**").addResourceLocations("classpath:/static/kgo/");
        	
      }

    
	// 設置跨領域設定
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOriginPatterns("*")
                .allowedMethods("*")
                .allowCredentials(true);
    }
    
	@Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        // 是否發送Cookie信息
        configuration.setAllowCredentials(true); //GEO changed 20210813 for testing true
        // 放行哪些原始域(頭部信息)
        configuration.setAllowedHeaders(Arrays.asList("*"));
        
        // 暴露哪些頭部信息（因為跨域訪問默認不能獲取全部頭部信息）
       configuration.setExposedHeaders(Arrays.asList("Content-Disposition","Set-Cookie"));

        // TODO　放行哪些原始域　設置domain
        //configuration.setAllowedOrigins(Arrays.asList("*"));
        
        // 放行哪些原始域(請求方式)
        configuration.setAllowedMethods(Arrays.asList("*"));
        configuration.setAllowedOriginPatterns(Arrays.asList("*"));
        
        // 無配置時，採用預設配置
        // configuration.applyPermitDefaultValues();

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
    
    /**
     * 多語系設定   
     * @return
     */   
    @Bean
    public LocaleResolver localeResolver() {
        SessionLocaleResolver slr = new SessionLocaleResolver();
        slr.setDefaultLocale(Locale.TAIWAN);
        return slr;
    }

    @Bean
    public LocaleChangeInterceptor localeChangeInterceptor() {
        LocaleChangeInterceptor lci = new LocaleChangeInterceptor();
        lci.setParamName("lang");
        return lci;
    }
    
    @Override
    public void addInterceptors(InterceptorRegistry registry){
        registry.addInterceptor(localeChangeInterceptor());
    }
    
    @Bean
    public HttpMessageConverters fastJsonHttpMessageConverters() {

       FastJsonHttpMessageConverter fastConverter = new FastJsonHttpMessageConverter();

       FastJsonConfig fastJsonConfig = new FastJsonConfig();

       fastJsonConfig.setSerializerFeatures(SerializerFeature.PrettyFormat);

       fastConverter.setFastJsonConfig(fastJsonConfig);

       HttpMessageConverter<?> converter = fastConverter;

       return new HttpMessageConverters(converter);
    }
   
}
