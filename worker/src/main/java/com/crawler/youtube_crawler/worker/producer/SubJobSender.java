package com.crawler.youtube_crawler.worker.producer;

import com.crawler.youtube_crawler.core.dto.SubJobDto;
import org.springframework.integration.annotation.MessagingGateway;

@MessagingGateway(defaultRequestChannel = "messageSubJobChannel")
public interface SubJobSender {
    void sendSubJob(final SubJobDto subjob);
}
