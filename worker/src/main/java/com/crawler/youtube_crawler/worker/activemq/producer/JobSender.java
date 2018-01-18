package com.crawler.youtube_crawler.worker.activemq.producer;

import com.crawler.youtube_crawler.core.dto.JobDto;
import org.springframework.integration.annotation.MessagingGateway;

//@MessagingGateway(defaultRequestChannel = "messageJobChannel")
public interface JobSender {
    void sendJob(final JobDto job);
}
