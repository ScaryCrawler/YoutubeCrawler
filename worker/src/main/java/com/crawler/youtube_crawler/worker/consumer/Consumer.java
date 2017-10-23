package com.crawler.youtube_crawler.worker.consumer;

import com.crawler.youtube_crawler.core.constants.JobStatus;
import com.crawler.youtube_crawler.core.dto.JobDto;
import com.crawler.youtube_crawler.core.repository.JobRepository;
import com.crawler.youtube_crawler.worker.handler.Handler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor
public class Consumer {
    private final JobRepository jobRepository;
    private final Handler handler;

    @ServiceActivator(inputChannel = "messageJobChannel")
    public final void consumeJob(JobDto job) {
        final UUID jobId = job.getId();

        log.info("Job " + jobId + " has been taken from queue");
        jobRepository.setStatus(jobId, JobStatus.IN_PROGRESS);

        final String status = handler.accept(job);

        log.info("Job " + jobId + " has been handled and finished with status");
        jobRepository.setStatus(jobId, status);
    }
}
