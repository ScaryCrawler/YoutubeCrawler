package com.crawler.youtube_crawler.core;

import com.google.api.client.util.DateTime;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Date;

@Configuration
public class Config {
    @Bean
    public DateTime dateTime(){
        return new DateTime(new Date().getTime());
    }
}
