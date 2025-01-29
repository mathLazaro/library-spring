package com.example.demoJpa.security;

import com.example.demoJpa.domain.Users;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;
import java.util.List;

@RequiredArgsConstructor
public class CustomAuthentication implements Authentication {

    private final Users user;

    @Override
    public Collection<GrantedAuthority> getAuthorities() {

        return List.of(new SimpleGrantedAuthority(user.getRoleUser().name()));
    }


    @Override
    public Object getCredentials() {

        return user.getPassword();
    }

    @Override
    public Users getDetails() {

        return user;
    }

    @Override
    public Users getPrincipal() {

        return user;
    }

    @Override
    public boolean isAuthenticated() {

        return user != null;
    }

    @Override
    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {

        if (isAuthenticated)
            throw new IllegalArgumentException();
    }

    @Override
    public String getName() {

        return user.getUsername();
    }

}
