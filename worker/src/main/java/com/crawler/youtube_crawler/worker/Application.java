package com.crawler.youtube_crawler.worker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@ComponentScan("com.crawler.youtube_crawler.*")
@EnableAsync
public class Application {
    public static void main(final String[] arguments) throws Exception {
        SpringApplication.run(Application.class, arguments);
    }
}
