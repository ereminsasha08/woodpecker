package com.woodpecker.controller;

import io.github.resilience4j.bulkhead.annotation.Bulkhead;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import jakarta.annotation.security.RolesAllowed;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.security.Principal;

import static com.woodpecker.controller.HelloController.REST_URL;

@RestController
@RequestMapping(REST_URL)
@RequiredArgsConstructor
public class HelloController {

    public static final String REST_URL = "/rest/hello";

    private final RestTemplate restTemplate;

    @GetMapping
    @CircuitBreaker(name = "hello", fallbackMethod = "noHello")
    @Bulkhead(name = "bulkheadHello")
    public String hello(Principal principal) {
        ResponseEntity<String> exchange = restTemplate.exchange("http://woodpeckerstore/rest/hello", HttpMethod.GET, null, String.class);
        return exchange.getBody();
    }

    private String noHello(Throwable t) {
        return "noHello";
    }
}
