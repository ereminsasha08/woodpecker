package com.woodpecker.woodpecker.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/maps").setViewName("maps");
        registry.addViewController("/users").setViewName("users");
        registry.addViewController("/orders").setViewName("orders");
    }
}