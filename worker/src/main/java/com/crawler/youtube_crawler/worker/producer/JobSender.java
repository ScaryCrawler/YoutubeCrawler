package com.crawler.youtube_crawler.worker.producer;

import com.crawler.youtube_crawler.core.dto.JobDto;
import org.springframework.integration.annotation.MessagingGateway;

//@MessagingGateway(defaultRequestChannel = "messageSubJobChannel")
public interface JobSender {
//    void sendSubJob(final JobDto subjob);
}

