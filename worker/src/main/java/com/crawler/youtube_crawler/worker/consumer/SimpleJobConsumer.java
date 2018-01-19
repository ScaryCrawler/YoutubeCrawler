package com.crawler.youtube_crawler.worker.consumer;

import com.crawler.youtube_crawler.core.constants.JobStatus;
import com.crawler.youtube_crawler.core.constants.JobType;
import com.crawler.youtube_crawler.core.dto.JobDto;
import com.crawler.youtube_crawler.core.repository.JobRepository;
import com.crawler.youtube_crawler.worker.handler.Handler;
import com.crawler.youtube_crawler.worker.producer.JobSender;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class SimpleJobConsumer implements JobConsumer {
    private final static String QUEUE_NAME = "job_queue";

    private final Handler handler;

    private final JobRepository jobRepository;
    private final JobSender jobSender;

    @Override
    public void consume(JobDto job) {
        if (! (JobStatus.NEW.equals(job.getStatus()) || JobStatus.FAILED.equals(job.getStatus()))) return;

        String status = handler.accept(job);

        job.setStatus(status);
        jobRepository.save(job);

        if (JobStatus.FAILED.equals(job.getStatus())) {
            jobSender.sendJob(job);
        }
    }

    @JmsListener(destination = QUEUE_NAME)
    public void onMessage(String message) {
        JobDto job;
        try {
            job = new ObjectMapper().readValue(message, JobDto.class);
            consume(job);
        } catch (Exception e) {
            log.error(e.getLocalizedMessage());
        }

    }
}
