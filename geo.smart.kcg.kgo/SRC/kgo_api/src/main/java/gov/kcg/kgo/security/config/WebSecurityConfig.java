package gov.kcg.kgo.security.config;

import gov.kcg.kgo.constant.SecurityConstant;
import gov.kcg.kgo.security.filter.IpBlacklistFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;

import javax.servlet.Filter;

//import gov.kcg.kgo.security.filter.AuthFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableScheduling
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private static Logger LOGGER = LoggerFactory.getLogger(WebSecurityConfig.class);

    @Autowired
    private AuthenticationService authenticationService;

//	@Autowired
//	private AuthEntryPointJwt unauthorizedHandler;

//	@Autowired
//	JwtConfigurer jwtConfigurer;

    @Value("${backend.logout.url}")
    private String backendLogoutUrl;

    @Value("${frontend.logout.url}")
    private String frontendLogoutUrl;

    @Autowired
    private CustomAuthenticationEntryPoint customAuthenticationEntryPoint;

    @Autowired
    private CustomAccessDeniedHandler customAccessDeniedHandler;

    /**
     * 不過濾白名單.
     */
    private static final String[] AUTH_WHITE_LIST = {
            // -- swagger ui
            "/v2/api-docs",
            "/swagger-resources",
            "/swagger-resources/**",
            "/configuration/ui",
            "/configuration/ui/**",
            "/configuration/security",
            "/swagger-ui.html",
            "/swagger-ui.html/**",
            // "/swagger-ui.html/swagger-resources/configuration/ui",
            // "/swagger-resources/configuration/ui",
            "/webjars/**",
            // for test
            "/backend/auth/**",
            "/backend/auth/loginTestAction",
//			"/backend/**",
            "/frontend/auth/**",
            "/frontend/auth/loginTestAction",
            "/frontend/auth/loginTestActionGet/**",
            "/backend/auth/logout",
            "/frontend/auth/logout",
            // TODO demo 暫時先加上白名單
            "/common/**",
            "/common/kcgPay/**",
            /**
             * Mock allow white list
            */

            "/backend/myData/**"

            /**
             * end of mock
            */
            // other public endpoints of your API may be appended to this array
    };

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        LOGGER.info("WebSecurityConfig call configure by http...");
        http
                .cors()
                .and()
                .csrf().disable()
                //		.exceptionHandling().authenticationEntryPoint(unauthorizedHandler).and()
                // TODO 啟用 API使用權限filter.
                //		.addFilterAfter(new AuthFilter(), BasicAuthenticationFilter.class)
                // 黑名單Filter
                .addFilterBefore(getIpBlacklistFilter(), FilterSecurityInterceptor.class)
                // .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                // .and()
                .authorizeRequests()
                //		.antMatchers("/backend/**").authenticated()
                // whitelist Swagger UI resources
                .antMatchers(AUTH_WHITE_LIST).permitAll()
                // TODO demo 暫時先拿掉
//			.antMatchers("/common/**").access(SecurityConstant.KGO_GOV_WHITE_LIST_JOIN_STRING)
                .antMatchers("/sp/apply/**").permitAll() //.access(SecurityConstant.KGO_GOV_WHITE_LIST_JOIN_STRING)
//                .antMatchers("/backend/**").hasRole(SecurityConstant.BACKEND)
                .antMatchers("/backend/**").permitAll()
                .antMatchers("/frontend/**").permitAll()
                .anyRequest().authenticated()
                .and().exceptionHandling()
                .accessDeniedHandler(customAccessDeniedHandler)
                .authenticationEntryPoint(customAuthenticationEntryPoint)
//				.and().logout()
//					.logoutUrl("/backend/auth/logout")
//					.logoutSuccessUrl(backendLogoutUrl)
//					.logoutUrl("/frontend/auth/logout")
//					.logoutSuccessUrl(frontendLogoutUrl)
                .and().csrf().disable();
        //		.antMatchers("/**").permitAll();

//		http.csrf().disable();
    }

//	public void configure(WebSecurity web) throws Exception {
//	    web.ignoring().antMatchers("/auth/login", "/auth/refreshToken");
//	}

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        LOGGER.info("WebSecurityConfig call configure by auth...");
        auth.authenticationProvider(authenticationService);
    }

    @Bean
    public Filter getIpBlacklistFilter() {
        return new IpBlacklistFilter();
    }
}
