package com.youtube_crawler.crawler.worker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@ComponentScan("com.youtube_crawler.crawler.worker.*")
@EnableAsync
public class Application {
    public static void main(final String[] arguments) throws Exception {
        SpringApplication.run(Application.class, arguments);
    }
}
