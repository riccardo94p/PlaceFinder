package com.example.PlaceFinder.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
@Configuration
public class PageConfig implements WebMvcConfigurer {

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        //registry.addViewController("/home").setViewName("home");
        registry.addViewController("/main").setViewName("main");
        registry.addViewController("/reservation").setViewName("reservation");
        registry.addViewController("/admin").setViewName("admin");
        registry.addViewController("/login").setViewName("login");
    }
}