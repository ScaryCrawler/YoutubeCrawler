package com.crawler.youtube_crawler.rest_api.service;

import com.crawler.youtube_crawler.core.constants.JobStatus;
import com.crawler.youtube_crawler.core.dto.JobDto;
import com.crawler.youtube_crawler.rest_api.producer.JobSender;
import com.crawler.youtube_crawler.core.repository.JobRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JobService {
    private final JobRepository jobRepository;
    private final JobSender jobSender;

    public final JobDto createJob(final JobDto jobDto) {
        final JobDto createdJob = jobRepository.create(jobDto);
        createdJob.setStatus(JobStatus.NEW);

        try {
            jobSender.sendJob(createdJob);
        }
        catch (Exception e){
            throw new RuntimeException(String.format("Failed to send message to Jms queue. Root cause: %s", e.getMessage()));
        }

        return createdJob;
    }
}
