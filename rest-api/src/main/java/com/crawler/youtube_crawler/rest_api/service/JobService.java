package com.crawler.youtube_crawler.rest_api.service;

import com.crawler.youtube_crawler.core.constants.JobStatus;
import com.crawler.youtube_crawler.core.dto.JobDto;
import com.crawler.youtube_crawler.rest_api.configuration.MessagingConfiguration;
import com.crawler.youtube_crawler.rest_api.producer.JobSender;
import com.crawler.youtube_crawler.rest_api.repository.JobRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class JobService {
    private final JobRepository jobRepository;
    private final JobSender jobSender;

    public final JobDto createJob(final JobDto jobDto) {
        final JobDto createdJob = jobRepository.save(jobDto);
        createdJob.setStatus(JobStatus.NEW);
        jobSender.sendJob(createdJob);
        return createdJob;
    }
}
