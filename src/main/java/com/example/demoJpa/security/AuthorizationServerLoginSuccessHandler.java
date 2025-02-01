package com.example.demoJpa.security;

import com.example.demoJpa.domain.Users;
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
public class AuthorizationServerLoginSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {
//
//    private U
//
//    @Override
//    public void onAuthenticationSuccess(HttpServletRequest request,
//                                        HttpServletResponse response,
//                                        Authentication authentication) throws ServletException, IOException {
//
//        OAuth2User principal = ((OAuth2AuthenticationToken) authentication).getPrincipal();
//        Users user = userService.getByEmail(principal.getAttribute("email")).orElse(null);
//        if (user == null)
//            user = persistUser(principal);
//        authentication = new CustomAuthentication(user);
//        SecurityContextHolder.getContext().setAuthentication(authentication);
//        response.sendRedirect("/books");
//    }

}
