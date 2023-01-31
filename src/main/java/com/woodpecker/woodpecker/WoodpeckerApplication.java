package com.woodpecker.woodpecker;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@RequiredArgsConstructor
public class WoodpeckerApplication {
    public static void main(String[] args) {
        SpringApplication.run(WoodpeckerApplication.class, args);
    }

}
