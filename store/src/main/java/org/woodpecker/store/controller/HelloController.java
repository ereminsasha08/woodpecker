package org.woodpecker.store.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

import static org.woodpecker.store.controller.HelloController.REST_URL;

@RestController
@RequestMapping(REST_URL)
@RequiredArgsConstructor
@Slf4j
public class HelloController {

    public static final String REST_URL = "/rest/hello";
    private final StreamBridge streamBridge;

    @GetMapping
    public String hello() {
        log.info("Hello");
        streamBridge.send("uppercase-out-0", LocalDateTime.now().toString());
        return "Hello store";
    }
}
