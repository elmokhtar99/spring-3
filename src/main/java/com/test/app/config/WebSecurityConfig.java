package com.test.app.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    public static final String ADMIN = "admin";
    public static final String USER = "user";
    private final JwtAuthConverter jwtAuthConverter;
    private static final String[] AUTH_WHITELIST = {"/swagger-resources", "/swagger-resources/**", "/configuration/ui",
            "/configuration/security", "/swagger-ui.html/**", "/webjars/**", "/v3/api-docs/**", "/v3/api-docs",
            "/public/**", "/public/authenticate", "/actuator/*", "/swagger-ui/**", "/api/api-docs/**","/test/anonymous","/test/anonymous/**"};


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.sessionManagement(s-> s.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authorizeHttpRequests(auth -> auth.requestMatchers(AUTH_WHITELIST).permitAll()
                    .requestMatchers(HttpMethod.GET, "/test/admin", "/test/admin/**").hasRole(USER)
                    .requestMatchers(HttpMethod.GET, "/test/user").hasAnyRole(ADMIN, USER)
                    .anyRequest().authenticated())
            .oauth2ResourceServer(httpSecurityOAuth2ResourceServerConfigurer -> httpSecurityOAuth2ResourceServerConfigurer.jwt(jwt -> jwt.jwtAuthenticationConverter(jwtAuthConverter)));
        return http.build();
    }

}