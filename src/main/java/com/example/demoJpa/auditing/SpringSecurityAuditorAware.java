package com.example.demoJpa.auditing;

import com.example.demoJpa.domain.Users;
import com.example.demoJpa.repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;

import java.util.Optional;


@RequiredArgsConstructor
public class SpringSecurityAuditorAware implements AuditorAware<Users> {

    private final UsersRepository usersRepository;

    @Override
    public Optional<Users> getCurrentAuditor() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            return Optional.empty();
        }
        User user = (User) authentication.getPrincipal();
        return usersRepository.findUsersByUsername(user.getUsername());
    }
}
