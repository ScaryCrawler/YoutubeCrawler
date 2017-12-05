package com.crawler.youtube_crawler.worker.consumer;

import com.crawler.youtube_crawler.core.constants.JobStatus;
import com.crawler.youtube_crawler.core.dto.JobDto;
import com.crawler.youtube_crawler.core.repository.JobRepository;
import com.crawler.youtube_crawler.worker.handler.JobHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor
public class JobConsumer {
    private final JobRepository jobRepository;
    private final JobHandler jobHandler;

    @ServiceActivator(inputChannel = "messageJobChannel")
    public final void consumeJob(JobDto job) {

        log.info("Job " + job.getId() + " has been taken from queue");
        job.setStatus(JobStatus.IN_PROGRESS);
        jobRepository.save(job);

        final String status = jobHandler.accept(job);

        log.info("Job " + job.getId() + " has been handled and finished with status");
        job.setStatus(status);
        jobRepository.save(job);
    }
}
