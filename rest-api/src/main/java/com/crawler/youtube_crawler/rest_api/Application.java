package com.crawler.youtube_crawler.rest_api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.web.MultipartAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Controller;

@SpringBootApplication
@Controller
@EnableAutoConfiguration(exclude={MultipartAutoConfiguration.class})
@ComponentScan("com.crawler.youtube_crawler.*")
@EnableJpaRepositories("com.crawler.youtube_crawler.core.repository")
@EntityScan("com.crawler.youtube_crawler.core.dto")
public class Application extends SpringBootServletInitializer {
    public static void main(final String[] arguments) throws Exception {
        SpringApplication.run(Application.class, arguments);
    }

    @Override
    protected final SpringApplicationBuilder configure(final SpringApplicationBuilder application) {
        return application.sources(Application.class);
    }

}
