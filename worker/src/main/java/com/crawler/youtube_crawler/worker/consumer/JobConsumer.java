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
    private final Handler jobHandler;
    private final JobRepository jobRepository;

    @ServiceActivator(inputChannel = "messageJobChannel")
    public final void consumeJob(JobDto job) {
        if (JobStatus.IN_PROGRESS.equals(job.getStatus()) || JobStatus.COMPLETED.equals(job.getStatus())) return;

        job.setStatus(JobStatus.IN_PROGRESS);
        jobRepository.save(job);

        String status = jobHandler.accept(job);
        jobRepository.save(job);

        if (JobStatus.FAILED.equals(status)){
            //todo: implement logic for resending job with producer
            while(JobStatus.FAILED.equals(status)){
                log.error(String.format("Job #%s (%s) failed. Process was restarted.", job.getId(), job.getType()));
                status = jobHandler.accept(job);
            }
        }
    }
}
