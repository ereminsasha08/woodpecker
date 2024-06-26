package org.woodpecker.store.config;

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
        http
                .oauth2ResourceServer(resource ->
                        resource
                                .jwt(jwt ->
                                        jwt
                                                .jwtAuthenticationConverter(new KeycloakJwtAuthenticationConverter())))

                .sessionManagement(session ->
                        session
                                .sessionCreationPolicy(SessionCreationPolicy.NEVER))

                .authorizeHttpRequests(request -> {
                    request
                            .anyRequest().permitAll();
//                            .requestMatchers("/rest/*").hasAnyAuthority("USER", "ADMIN")
//                            .anyRequest().fullyAuthenticated();
                });
        return http.build();
    }
}