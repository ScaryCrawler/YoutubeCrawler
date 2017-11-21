package com.crawler.youtube_crawler.rest_api.producer;

import com.crawler.youtube_crawler.core.dto.JobDto;
import org.springframework.integration.annotation.MessagingGateway;

@MessagingGateway(defaultRequestChannel = "messageJobChannel")
public interface JobSender {
    void sendJob(final JobDto job);
}
