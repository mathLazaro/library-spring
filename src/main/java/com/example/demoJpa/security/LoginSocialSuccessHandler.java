package com.example.demoJpa.security;

import com.example.demoJpa.domain.RoleUser;
import com.example.demoJpa.domain.Users;
import com.example.demoJpa.service.UserService;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class LoginSocialSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    private final UserService userService;
    private final String DEFAULT_PASSWORD = "senha123";
    private final ServletContext servletContext;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws ServletException, IOException {

        OAuth2User principal = ((OAuth2AuthenticationToken) authentication).getPrincipal();
        Users user = userService.getByEmail(principal.getAttribute("email")).orElse(null);
        if (user == null)
            user = persistUser(principal);
        authentication = new CustomAuthentication(user);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        response.sendRedirect("/books");
    }

    private Users persistUser(OAuth2User principal) {

        String email = principal.getAttribute("email");
        Users user = Users.builder()
                .name(principal.getAttribute("name"))
                .email(email)
                .username(extractUsername(email))
                .password(DEFAULT_PASSWORD)
                .roleUser(RoleUser.OPERATOR)
                .build();
        return userService.persist(user);
    }

    private String extractUsername(String email) {

        return email == null ? null : email.substring(0, email.indexOf("@"));
    }
}
