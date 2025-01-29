package com.example.demoJpa.auditing;

import com.example.demoJpa.domain.Users;
import com.example.demoJpa.security.CustomAuthentication;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;


public class SpringSecurityAuditorAware implements AuditorAware<Users> {

    @Override
    public Optional<Users> getCurrentAuditor() {

        CustomAuthentication authentication = (CustomAuthentication) SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            return Optional.empty();
        }
        return Optional.of(authentication.getPrincipal());
    }
}
