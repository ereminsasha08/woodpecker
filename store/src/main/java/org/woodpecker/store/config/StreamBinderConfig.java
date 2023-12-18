package org.woodpecker.store.config;

import org.springframework.context.annotation.Configuration;

import java.util.Random;

@Configuration
public class StreamBinderConfig {
    Random random = new Random();

//    @Bean
//    public Supplier<String> uppercase() {
//        return () -> String.valueOf(random.nextInt(5));
//
//    }
}
