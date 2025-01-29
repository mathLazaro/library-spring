package com.example.demoJpa.configuration;

import com.example.demoJpa.domain.Users;
import com.example.demoJpa.repository.UsersRepository;
import com.example.demoJpa.auditing.SpringSecurityAuditorAware;
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
