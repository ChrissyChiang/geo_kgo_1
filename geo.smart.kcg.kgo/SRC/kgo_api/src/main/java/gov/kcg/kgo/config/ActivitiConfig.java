package gov.kcg.kgo.config;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.sql.DataSource;

import org.activiti.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.activiti.engine.impl.history.HistoryLevel;
import org.activiti.spring.SpringProcessEngineConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
public class ActivitiConfig {
	@Value("${activiti.datasource.url}")
	private String url;

	@Value("${activiti.datasource.username}")
	private String username;

	@Value("${activiti.datasource.password}")
	private String password;

	@Value("${activiti.datasource.driver-class-name}")
	private String driverClassName;

    @Autowired
    private DataSource dataSource;
    
    @Autowired
    PlatformTransactionManager transactionManager;
	/**
	 * 配置Activiti Data Source
	 * 
	 * @throws ClassNotFoundException
	 */

//	@SuppressWarnings("unchecked")
//	@Bean
//	public DataSource activitiDataSource() throws ClassNotFoundException {
//		SimpleDriverDataSource dataSource = new SimpleDriverDataSource();
//		dataSource.setUrl(url);
//		dataSource.setDriverClass((Class<? extends Driver>) Class.forName(driverClassName));
//		dataSource.setUsername(username);
//		dataSource.setPassword(password);
//		return dataSource;
//	}

	/**
	 * 配置Transaction Manager
	 * 
	 * @throws ClassNotFoundException
	 */
//	@Bean
//	public PlatformTransactionManager platformTransactionManager() throws ClassNotFoundException {
//		return new DataSourceTransactionManager(activitiDataSource());
//	}
	
//    @Bean(name = "transactionManager")
//	public PlatformTransactionManager transactionManager() {
//		JpaTransactionManager tm = new JpaTransactionManager();
//		tm.setEntityManagerFactory(emf);
//		tm.setDataSource(dataSource);
//		return tm;
//	}

	/**
	 * 配置並產生流程引擎設置實例
	 * 
	 * @throws ClassNotFoundException
	 */
	@Bean
	public SpringProcessEngineConfiguration processEngineConfiguration() throws ClassNotFoundException {
		SpringProcessEngineConfiguration processEngineConfiguration = new SpringProcessEngineConfiguration();
		processEngineConfiguration.setHistoryLevel(HistoryLevel.FULL);
		processEngineConfiguration.setDataSource(dataSource);
		 processEngineConfiguration.setDatabaseSchemaUpdate("true");
		processEngineConfiguration.setTransactionManager(transactionManager);
		 processEngineConfiguration.setAsyncExecutorActivate(true);
		processEngineConfiguration.setHistory("full");

		return processEngineConfiguration;
	}

	/**
	 * https://www.codetd.com/en/article/8295265
	 * 
	 * error of latest activiti-spring-boot-starter > 7.1.0.M5 version
	 * 
	 * @return
	 */
//	@Bean
//	public UserDetailsService myUserDetailsService() {
//
//		InMemoryUserDetailsManager inMemoryUserDetailsManager = new InMemoryUserDetailsManager();
//
////		String[][] usersGroupsAndRoles = { { "salaboy", "password", "ROLE_ACTIVITI_USER", "GROUP_activitiTeam" },
////				{ "ryandawsonuk", "password", "ROLE_ACTIVITI_USER", "GROUP_activitiTeam" },
////				{ "erdemedeiros", "password", "ROLE_ACTIVITI_USER", "GROUP_activitiTeam" },
////				{ "other", "password", "ROLE_ACTIVITI_USER", "GROUP_otherTeam" },
////				{ "admin", "password", "ROLE_ACTIVITI_ADMIN" }, };
////
////		for (String[] user : usersGroupsAndRoles) {
////			List<String> authoritiesStrings = Arrays.asList(Arrays.copyOfRange(user, 2, user.length));
////			inMemoryUserDetailsManager.createUser(new User(user[0], passwordEncoder().encode(user[1]),
////					authoritiesStrings.stream().map(s -> new SimpleGrantedAuthority(s)).collect(Collectors.toList())));
////		}
//
//		return inMemoryUserDetailsManager;
//	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

}
