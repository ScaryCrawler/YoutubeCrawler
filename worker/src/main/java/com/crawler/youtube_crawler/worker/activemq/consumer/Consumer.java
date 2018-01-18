package com.crawler.youtube_crawler.worker.activemq.consumer;

import com.crawler.youtube_crawler.core.constants.JobStatus;
import com.crawler.youtube_crawler.core.dto.JobDto;
import com.crawler.youtube_crawler.core.repository.JobRepository;
import com.crawler.youtube_crawler.worker.handler.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.stereotype.Component;


@Slf4j
@Component
@RequiredArgsConstructor
public class Consumer {
    private final JobRepository jobRepository;
    private final SimpleHandler handler;



    @ServiceActivator(inputChannel = "messageJobChannel")
    public final void consumeJob(JobDto job) {
        if (! (JobStatus.NEW.equals(job.getStatus()) && JobStatus.FAILED.equals(job.getStatus()))) return;

        String status = handler.accept(job);

        jobRepository.setStatus(job.getId(), status);
    }
}
