package com.example.demoJpa.security;

import com.example.demoJpa.domain.Users;
import com.example.demoJpa.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class CustomAuthenticationProvider implements AuthenticationProvider {

    private final UserService userService;
    private final PasswordEncoder encoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        String username = authentication.getName();
        String rawPassword = authentication.getCredentials().toString();

        Optional<Users> userOpt = userService.getByLogin(username);
        if (userOpt.isEmpty())
            throw new AuthenticationCredentialsNotFoundException("Usuário e/ou senha inválido(s)");
        Users userOnDB= userOpt.get();
        if (!encoder.matches(rawPassword, userOnDB.getPassword()))
            throw new AuthenticationCredentialsNotFoundException("Usuário e/ou senha inválido(s)");

        return new CustomAuthentication(userOnDB);
    }

    @Override
    public boolean supports(Class<?> authentication) {

        return authentication.isAssignableFrom(UsernamePasswordAuthenticationToken.class);
    }
}
