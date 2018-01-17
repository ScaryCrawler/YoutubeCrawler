package com.crawler.youtube_crawler.worker;

import com.crawler.youtube_crawler.worker.handler.CommentHandler;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@ComponentScan("com.crawler.youtube_crawler.*")
@EnableAsync
public class Application {
    public static void main(final String[] arguments) throws Exception {
        ApplicationContext context =  SpringApplication.run(Application.class, arguments);

    }
}
