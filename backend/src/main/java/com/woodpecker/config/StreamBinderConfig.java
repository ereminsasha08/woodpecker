package com.woodpecker.config;

import com.woodpecker.repository.redis.StringWrapperRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Consumer;

@Configuration
@RequiredArgsConstructor
public class StreamBinderConfig {

    private final StringWrapperRepository stringWrapperRepository;

    @Bean
    public Consumer<String> uppercase() {
        return value -> {
//            stringWrapperRepository.deleteByString(value);
//            stringWrapperRepository.findAll().forEach(System.out::println);
            System.out.println("Received: " + value);
        };
    }
}
