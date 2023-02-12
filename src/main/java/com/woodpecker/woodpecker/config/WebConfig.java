package com.woodpecker.woodpecker.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/users").setViewName("users");
        registry.addViewController("/maps").setViewName("maps");
        registry.addViewController("/cut").setViewName("cut");
        registry.addViewController("/paint").setViewName("paint");
        registry.addViewController("/production").setViewName("production");
        registry.addViewController("/availability").setViewName("availability");

    }
}
