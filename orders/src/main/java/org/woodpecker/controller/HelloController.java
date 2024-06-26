package org.woodpecker.controller;

import brave.ScopedSpan;
import brave.Tracer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.woodpecker.repository.redis.StringWrapper;
import org.woodpecker.repository.redis.StringWrapperRepository;
import io.github.resilience4j.bulkhead.annotation.Bulkhead;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.security.Principal;

import static org.woodpecker.controller.HelloController.REST_URL;

@RestController
@RequestMapping(REST_URL)
@Slf4j
public class HelloController {

    public static final String REST_URL = "/rest/hello";
    private final RestTemplate restTemplate;
    private final StringWrapperRepository stringWrapperRepository;
    private final Tracer tracer;

    public HelloController(@Qualifier("innerRestTemplate") RestTemplate restTemplate, StringWrapperRepository stringWrapperRepository, Tracer tracer) {
        this.restTemplate = restTemplate;
        this.stringWrapperRepository = stringWrapperRepository;
        this.tracer = tracer;
    }

    @GetMapping
    @CircuitBreaker(name = "hello", fallbackMethod = "noHello")
    @Bulkhead(name = "bulkheadHello")
    public String hello(Principal principal) {
        StringWrapper arg = findByName("Hi");
        log.info("Redis {}", arg);
        ResponseEntity<String> exchange = restTemplate.exchange("http://woodpeckerstore/rest/hello", HttpMethod.GET, null, String.class);
        return exchange.getBody();
    }

    private StringWrapper findByName(String name) {
        ScopedSpan redisFindByName = tracer.startScopedSpan("redisFindByName");
        try {
            return stringWrapperRepository.findByName(name).orElseGet(() -> StringWrapper.builder().name("notFound").build());
        } finally {
            redisFindByName.tag("peer.service", "redis");
            redisFindByName.annotate("Find in redis");
            redisFindByName.finish();
        }
    }

    private String noHello(Throwable t) {
        return "noHello";
    }
}
