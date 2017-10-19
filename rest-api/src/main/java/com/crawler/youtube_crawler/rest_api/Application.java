package com.crawler.youtube_crawler.rest_api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.MultipartAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@SpringBootApplication
@Controller
@EnableAutoConfiguration(exclude={MultipartAutoConfiguration.class})
@ComponentScan("com.crawler.youtube_crawler.rest_api")
public class Application extends SpringBootServletInitializer {

    public static void main(final String[] arguments) throws Exception {
        SpringApplication.run(Application.class, arguments);
    }

    @Override
    protected final SpringApplicationBuilder configure(final SpringApplicationBuilder application) {
        return application.sources(Application.class);
    }

    @RequestMapping(value = {"/{path:^(?!(?:images|dist|fonts|services|css|scripts|libraries|webjars|swagger-ui.html|index.html)).*}/**/*", "/{path:^(?!(?:images|dist|fonts|services|css|scripts|libraries|webjars|swagger-ui.html|index.html)).*}"})
    public final String redirect(@PathVariable("path") String path) {
        return "forward:/";
    }
}
