package com.example.playlistsync.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CrossOriginsConf implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("https://playlist-sync-demo.herokuapp.com")
                .allowedHeaders("https://playlist-sync-demo.herokuapp.com")
                .allowedMethods("*");

    }
}
