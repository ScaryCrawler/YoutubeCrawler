package com.crawler.youtube_crawler.rest_api.producer;

import com.crawler.youtube_crawler.core.dto.JobDto;
import com.crawler.youtube_crawler.rest_api.configuration.MessagingConfiguration;
import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;
import org.springframework.integration.annotation.Publisher;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.Payload;

@MessagingGateway(defaultRequestChannel = "messageJobChannel")
public interface JobSender {
    void sendJob(final JobDto job);
}
