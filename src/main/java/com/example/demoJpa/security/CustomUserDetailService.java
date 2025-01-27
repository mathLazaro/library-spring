package com.example.demoJpa.security;

import com.example.demoJpa.domain.Users;
import com.example.demoJpa.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

@RequiredArgsConstructor
public class CustomUserDetailService implements UserDetailsService {

    private final UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Users> response = userService.getByLogin(username);

        if (response.isEmpty())
            throw new UsernameNotFoundException("Usuário não encontrado");
        Users user = response.get();

        return User.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .roles(user.getRoleUser().name())
                .build();
    }
}
