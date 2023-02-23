package com.woodpecker.woodpecker.config;

import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@EnableCaching
@Configuration
@Profile(value = "prod")
public class CacheConfig {
}
