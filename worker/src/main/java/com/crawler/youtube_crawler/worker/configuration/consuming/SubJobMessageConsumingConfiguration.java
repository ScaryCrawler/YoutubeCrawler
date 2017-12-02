package com.crawler.youtube_crawler.worker.configuration.consuming;

import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.IntegrationComponentScan;
import org.springframework.integration.config.EnableIntegration;

@Configuration
@EnableIntegration
@IntegrationComponentScan(basePackages = "com.crawler.youtube_crawler.worker")
public abstract class SubJobMessageConsumingConfiguration extends MessageConsumingConfiguration {
    //TODO: move all these constants to properties
    final static String QUEUE_NAME = "subjob_queue";

    public SubJobMessageConsumingConfiguration() {
        super(QUEUE_NAME);
    }
}
