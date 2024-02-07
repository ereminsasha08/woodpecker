package org.woodpecker.service.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.client.RestTemplate;

@EnableDiscoveryClient
@Configuration
@Slf4j
public class EurekaClientConfig {


    @LoadBalanced
    @Bean(name = "innerRestTemplate")
    public RestTemplate getRestTemplate(RestTemplateBuilder builder) {
        RestTemplate restTemplate = builder.build();
        restTemplate.getInterceptors().add(interceptor());
        return restTemplate;
    }

    private ClientHttpRequestInterceptor interceptor() {
        return (request, body, execution) -> {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication instanceof JwtAuthenticationToken) {
                String token = "Bearer " + ((JwtAuthenticationToken) authentication).getToken().getTokenValue();
                request.getHeaders().add(HttpHeaders.AUTHORIZATION, token);
                log.debug("Added header 'Authorization: {}'", token);
            }
            return execution.execute(request, body);
        };
    }
}
