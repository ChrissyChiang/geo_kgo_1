package gov.kcg.kgo.config;
//package com.cub.cathay360.config;
//import javax.persistence.EntityManagerFactory;
//import javax.sql.DataSource;
//
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.boot.context.properties.ConfigurationProperties;
//import org.springframework.boot.jdbc.DataSourceBuilder;
//import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.Primary;
//import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
//import org.springframework.jdbc.core.JdbcTemplate;
//import org.springframework.orm.jpa.JpaTransactionManager;
//import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
//import org.springframework.transaction.PlatformTransactionManager;
//import org.springframework.transaction.annotation.EnableTransactionManagement;
//
//@EnableTransactionManagement
//@EnableJpaRepositories(entityManagerFactoryRef = "firstEntityManagerFactory",
//        transactionManagerRef = "firstTransactionManager",
//        basePackages = { "com.cub.cathay360.repository" })
//@Configuration
//public class FirstDataSourceConfig {
//    @Primary
//    @ConfigurationProperties(prefix = "spring.datasource")
//    @Bean(name = "firstDataSource")
//    public DataSource firstDataSource () {
//        return DataSourceBuilder.create().build();
//    }
//
//    @Primary
//    @Bean(name = "firstEntityManagerFactory")
//    public LocalContainerEntityManagerFactoryBean firstEntityManagerFactory (
//            EntityManagerFactoryBuilder builder,
//            @Qualifier("firstDataSource") DataSource dataSource) {
//        return builder.dataSource(dataSource)
//                .packages("com.cub.cathay360.model", "com.cub.cathay360.dto")
//                .persistenceUnit("integeratoer").build();
//    }
//
//    @Primary
//    @Bean(name = "firstTransactionManager")
//    public PlatformTransactionManager firstTransactionManager (
//            @Qualifier("firstEntityManagerFactory") EntityManagerFactory entityManagerFactory) {
//        return new JpaTransactionManager(entityManagerFactory);
//    }
//
//    // First Datasource JdbcTemplate
//    @Bean(name = "firstJdbcTemplate")
//    public JdbcTemplate firstTemplate(@Qualifier("firstDataSource") DataSource dataSource) {
//        return new JdbcTemplate(dataSource);
//    }
//}