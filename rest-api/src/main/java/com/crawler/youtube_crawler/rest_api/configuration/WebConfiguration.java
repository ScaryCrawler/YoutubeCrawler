package com.crawler.youtube_crawler.rest_api.configuration;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
@RequiredArgsConstructor
@EnableWebMvc
public class WebConfiguration extends WebMvcConfigurerAdapter {
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addRedirectViewController("/swagger-resources/com.crawler.youtube_crawler.rest_api.configuration/ui", "/swagger-resources/com.crawler.youtube_crawler.rest_api.configuration/ui");
        registry.addRedirectViewController("/swagger-resources/com.crawler.youtube_crawler.rest_api.configuration/security", "/swagger-resources/com.crawler.youtube_crawler.rest_api.configuration/security");
        registry.addRedirectViewController("/swagger-resources", "/swagger-resources");
        registry.addRedirectViewController("/v2/api-docs", "/v2/api-docs");
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.
                addResourceHandler("/swagger-ui.html**").addResourceLocations("classpath:/META-INF/resources/swagger-ui.html");
        registry.
                addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
    }
}
