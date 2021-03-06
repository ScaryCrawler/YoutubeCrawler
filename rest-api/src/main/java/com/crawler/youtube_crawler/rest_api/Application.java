package com.crawler.youtube_crawler.rest_api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.MultipartAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.stereotype.Controller;

@SpringBootApplication
@Controller
@EnableAutoConfiguration(exclude={MultipartAutoConfiguration.class})
@ComponentScan("com.crawler.youtube_crawler.*")
@EnableMongoRepositories(basePackages = "com.crawler.youtube_crawler.*")
public class Application extends SpringBootServletInitializer {
    public static void main(final String[] arguments) throws Exception {
        SpringApplication.run(Application.class, arguments);
    }

    @Override
    protected final SpringApplicationBuilder configure(final SpringApplicationBuilder application) {
        return application.sources(Application.class);
    }

}
