package com.brobono.samosawebapp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

@Configuration
public class NoOpDataSourceConfig {

    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource noOpDataSource = new DriverManagerDataSource();
        noOpDataSource.setUrl("jdbc:h2:mem:no-op");
        noOpDataSource.setDriverClassName("org.h2.Driver");
        noOpDataSource.setUsername("sa");
        noOpDataSource.setPassword("");

        return noOpDataSource;
    }
}
