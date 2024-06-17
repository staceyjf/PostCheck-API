package com.auspost.postcode.config.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import jakarta.servlet.DispatcherType;

@Configuration
@EnableWebSecurity
// configures authentication and authorization
public class AuthConfig {
    @Autowired
    SecurityFilter securityFilter; // custom filter to extra and validate token

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .csrf(csrf -> csrf.disable()) // Cross-Site Request Forgery
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                // won't store any state about client requests
                .authorizeHttpRequests(authorize -> authorize
                        .dispatcherTypeMatchers(DispatcherType.FORWARD, DispatcherType.ERROR).permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/v1/postcodes/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/v1/postcodes").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PATCH, "/api/v1/postcodes/{id}").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/api/v1/suburbs/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/v1/suburbs").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PATCH, "/api/v1/suburbs/{id}").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.POST, "/api/v1/auth/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/v1/auth/{id}").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/v1/auth/**").hasAnyRole("ADMIN", "USER")
                        .anyRequest().authenticated()) // catch all to ensure unnamed endpoints need auth
                .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    // interface that is used to auth user's credentials
    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
            throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    // interface for encoding passwords using BCrypt strong hashing function
    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
