package com.auspost.postcode.config;

import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

// creates a connections factory for creating connections with a relational database
// sqlite is not supported out of the box so additional config is required 
@Configuration
public class DbConfig {
    @Autowired
    Environment env;

    @Bean
    public DataSource dataSource() {
        final DriverManagerDataSource dataSource = new DriverManagerDataSource();
        String driverClassName = env.getProperty("spring.datasource.driver-class-name");

        if (driverClassName != null) {
            dataSource.setDriverClassName(driverClassName);
        } else {
            throw new IllegalArgumentException("Property 'spring.datasource.driver-class-name' not found");
        }

        dataSource.setUrl(env.getProperty("spring.datasource.url"));
        return dataSource;
    }
}
