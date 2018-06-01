package com.spectech.ambacambackend.configuration.security;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = "com.spectech.ambacambackend.repository")
@PropertySource("classpath:application.properties")
@EntityScan(basePackages = { "com.spectech.ambacambackend.model" })
public class JPAPersistenceConfig {

}