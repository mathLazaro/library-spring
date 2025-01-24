package com.example.demoJpa.configuration;

import com.example.demoJpa.domain.RoleUser;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .csrf(AbstractHttpConfigurer::disable)
                .httpBasic(Customizer.withDefaults())
                .formLogin(Customizer.withDefaults())
                .authorizeHttpRequests(authorize -> {
                    authorize.requestMatchers("/books/**").hasAnyRole(RoleUser.ADMIN.name(), RoleUser.OPERATOR.name());
                    authorize.requestMatchers(HttpMethod.DELETE, "/authors/**").hasRole(RoleUser.ADMIN.name());
                    authorize.requestMatchers(HttpMethod.GET, "/authors/**").hasAnyRole(RoleUser.ADMIN.name(), RoleUser.OPERATOR.name());
                    authorize.requestMatchers(HttpMethod.POST, "/authors").hasRole(RoleUser.ADMIN.name());
                    authorize.requestMatchers(HttpMethod.PUT, "/authors/**").hasRole(RoleUser.ADMIN.name());
                    authorize.anyRequest().authenticated();
                })
                .build();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(5);
    }

//    @Bean
//    @Primary
//    public UserDetailsService users(DataSource dataSource, PasswordEncoder encoder) {
//        UserDetails user = User.builder()
//                .username("user")
//                .password(encoder.encode("123"))
//                .roles(RoleUser.OPERATOR.name())
//                .build();
//        UserDetails admin = User.builder()
//                .username("admin")
//                .password(encoder.encode("123"))
//                .roles(RoleUser.ADMIN.name())
//                .build();
//        JdbcUserDetailsManager users = new JdbcUserDetailsManager(dataSource);
//        users.createUser(user);
//        users.createUser(admin);
//        return users;
//    }

    @Bean
    public UserDetailsService userDetailsService(PasswordEncoder encoder) {
        UserDetails userAdmin = User.builder()
                .username("admin")
                .password(encoder.encode("123"))
                .roles(RoleUser.ADMIN.name())
                .build();

        return new InMemoryUserDetailsManager(userAdmin);
    }
}
