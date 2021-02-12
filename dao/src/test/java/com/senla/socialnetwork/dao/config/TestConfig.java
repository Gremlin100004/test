package com.senla.socialnetwork.dao.config;

import liquibase.integration.spring.SpringLiquibase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

@Configuration
@ComponentScan("com.senla.socialnetwork.dao")
@PropertySource("classpath:application.properties")
public class TestConfig {
    private static final String CHANGELOG_PATH = "classpath:db/changelog/changeLog-master.xml";
    private static final String DATA_SOURCE_PACKAGE = "socialnetwork.datasource.package";
    private static final String CONNECTION_URL = "hibernate.connection.url";
    private static final String DRIVER_DATABASE = "hibernate.connection.driver_class";
    private static final String USERNAME = "hibernate.connection.username";
    private static final String PASSWORD = "hibernate.connection.password";

    @Bean
    public DataSource dataSource(final Environment environment) {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(environment.getRequiredProperty(DRIVER_DATABASE));
        dataSource.setUrl(environment.getRequiredProperty(CONNECTION_URL));
        dataSource.setUsername(environment.getRequiredProperty(USERNAME));
        dataSource.setPassword(environment.getRequiredProperty(PASSWORD));
        return dataSource;
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(final Environment environment) {
        LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
        entityManagerFactoryBean.setDataSource(dataSource(environment));
        entityManagerFactoryBean.setPackagesToScan(environment.getRequiredProperty(DATA_SOURCE_PACKAGE));
        JpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        entityManagerFactoryBean.setJpaVendorAdapter(vendorAdapter);
        return entityManagerFactoryBean;
    }

    @Bean
    public PlatformTransactionManager transactionManager(final Environment environment) {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerFactory(environment).getObject());
        return transactionManager;
    }

    @Bean
    public JdbcTemplate jdbcTemplate(final Environment environment) {
        return new JdbcTemplate(dataSource(environment));
    }

    @Bean
    public SpringLiquibase liquibase(final Environment environment) {
        SpringLiquibase liquibase = new SpringLiquibase();
        liquibase.setChangeLog(CHANGELOG_PATH);
        liquibase.setDataSource(dataSource(environment));
        return liquibase;
    }

}
