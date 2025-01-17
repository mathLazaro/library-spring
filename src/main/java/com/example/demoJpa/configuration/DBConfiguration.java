package com.example.demoJpa.configuration;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

@Configuration
public class DBConfiguration {

    @Value("${spring.datasource.url}")
    private String url;
    @Value("${spring.datasource.username}")
    private String username;
    @Value("${spring.datasource.driver-class-name}")
    private String driver;

//    @Bean
    public DataSource dataSourcePostgres() {

        DriverManagerDataSource manager = new DriverManagerDataSource();
        manager.setUrl(url);
        manager.setUsername(username);
        manager.setDriverClassName(driver);
        return manager;
    }

//    @Bean
    public DataSource hikariDataSource() {

        HikariConfig config = new HikariConfig();
        config.setDriverClassName(driver);
        config.setJdbcUrl(url);
        config.setUsername(username);
        config.setPassword("");
        config.setMaximumPoolSize(10);
        config.setMinimumIdle(1);
        config.setConnectionTestQuery("select 1");

        return new HikariDataSource(config);
    }

}
