package com.woodpecker.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@Slf4j
@RequiredArgsConstructor
public class SecurityConfiguration {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//        http
//                .cors(Customizer.withDefaults())
//                .httpBasic(Customizer.withDefaults())
//                .csrf(csrf ->
//                        csrf
//                                .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
//                )
//                .rememberMe(rememberMe -> {
//                    rememberMe.key(key);
//                    rememberMe.alwaysRemember(true);
//                });
        http
                .oauth2ResourceServer(resource ->
                        resource.jwt(jwt -> jwt
                                .jwtAuthenticationConverter(new KeycloakJwtAuthenticationConverter()))
                );
        http
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.NEVER)
                );
        http
                .authorizeHttpRequests(request -> {
                    request
                            .requestMatchers("/unauthenticated", "/oauth2/**", "/login/**").permitAll()
                            .requestMatchers("/rest/hello").hasAnyAuthority("USER", "ADMIN")
                            .requestMatchers("/rest/admin/**").hasAnyAuthority("ADMIN")
                            .requestMatchers("/admin/**").hasAnyAuthority("ADMIN")
                            .anyRequest().fullyAuthenticated();
                });
//        http.formLogin(
//                login ->
//                        login
//                                .loginPage("http://localhost:8072/auth")
//        );
        return http.build();
    }

}