package com.example.image.db;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import com.zaxxer.hikari.HikariConfig;

@Configuration
public class DBConfig {

    @Bean
    @Scope(scopeName = "prototype")
    @ConfigurationProperties(prefix="spring.datasource.hikari")
    public HikariConfig getHikariConfig() {
        return new HikariConfig();
    }
}
