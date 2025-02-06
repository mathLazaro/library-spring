package com.example.demoJpa.configuration;

import com.example.demoJpa.auditing.SpringSecurityAuditorAware;
import com.example.demoJpa.domain.Users;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;

@Configuration
public class AuditingConfiguration {

    @Bean
    public AuditorAware<Users> springSecurityAuditorAware() {
        return new SpringSecurityAuditorAware();
    }
}
