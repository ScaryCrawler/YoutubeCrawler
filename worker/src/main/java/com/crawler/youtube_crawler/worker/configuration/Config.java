package com.crawler.youtube_crawler.worker.configuration;

import com.crawler.youtube_crawler.worker.youtubeapi.Auth;
import com.google.api.services.youtube.YouTube;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Config {

    @Bean
    YouTube youtube() {
        return new YouTube.Builder(Auth.HTTP_TRANSPORT, Auth.JSON_FACTORY, request -> {})
                .setApplicationName("youtube-search")
                .build();
    }
}
