package com.crawler.youtube_crawler.worker.consumer;

import com.crawler.youtube_crawler.core.constants.JobStatus;
import com.crawler.youtube_crawler.core.dto.JobDto;
import com.crawler.youtube_crawler.core.repository.JobRepository;
import com.crawler.youtube_crawler.worker.handler.Handler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class JobConsumer {
    private final JobRepository jobRepository;
    private final Handler jobHandler;

    @ServiceActivator(inputChannel = "messageJobChannel")
    public final void consumeJob(JobDto job) {

        log.info("Job " + job.getId() + " has been taken from queue");
        job.setStatus(JobStatus.IN_PROGRESS);
        jobRepository.create(job);

        final String status = jobHandler.accept(job);

        log.info("Job " + job.getId() + " has been handled and finished with status");
        job.setStatus(status);
        jobRepository.create(job);
    }
}
