package com.gemini.ambacam.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import com.gemini.ambacam.service.AuditorService;

@Configuration
@EnableJpaAuditing(auditorAwareRef = "auditorAware")
public class JpaAuditingConfig {
	@Bean
	public AuditorAware<String> auditorAware() {
		return new AuditorService();
	}
}
