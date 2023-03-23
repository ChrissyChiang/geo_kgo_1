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
//import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
//import org.springframework.jdbc.core.JdbcTemplate;
//import org.springframework.orm.jpa.JpaTransactionManager;
//import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
//import org.springframework.transaction.PlatformTransactionManager;
//import org.springframework.transaction.annotation.EnableTransactionManagement;
//
//@EnableTransactionManagement
//@EnableJpaRepositories(entityManagerFactoryRef = "secondEntityManagerFactory",
//        transactionManagerRef = "secondTransactionManager",
//        basePackages = { "com.cub.cathay360.secRep" })
//@Configuration
//public class SecondDataSourceConfig {
//
//    @ConfigurationProperties(prefix = "spring.second.datasource")
//    @Bean(name = "secondDataSource")
//    public DataSource secondDataSource () {
//        return DataSourceBuilder.create().build();
//    }
//
//    @Bean(name = "secondEntityManagerFactory")
//    public LocalContainerEntityManagerFactoryBean secondEntityManagerFactory (
//            EntityManagerFactoryBuilder builder,
//            @Qualifier("secondDataSource") DataSource dataSource) {
//        return builder.dataSource(dataSource)
//                .packages("com.cub.cathay360.secDbModel")
//                .persistenceUnit("second").build();
//    }
//
//    @Bean(name = "secondTransactionManager")
//    public PlatformTransactionManager secondTransactionManager (
//            @Qualifier("secondEntityManagerFactory") EntityManagerFactory entityManagerFactory) {
//        return new JpaTransactionManager(entityManagerFactory);
//    }
//
//    // Second Datasource JdbcTemplate
//    @Bean(name = "secondJdbcTemplate")
//    public JdbcTemplate secondTemplate(@Qualifier("secondDataSource") DataSource dataSource) {
//        return new JdbcTemplate(dataSource);
//    }
//
//}