package com.crawler.youtube_crawler.worker.configuration.consuming;

import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.IntegrationComponentScan;
import org.springframework.integration.config.EnableIntegration;


@Configuration
@EnableIntegration
@IntegrationComponentScan(basePackages = "com.crawler.youtube_crawler.worker")
public abstract class JobMessageConsumingConfiguration extends MessageConsumingConfiguration{
    final static String QUEUE_NAME = "job_queue";

    public JobMessageConsumingConfiguration() {
        super(QUEUE_NAME);
    }
}
