package com.crawler.youtube_crawler.rest_api.service;

import com.crawler.youtube_crawler.core.constants.JobStatus;
import com.crawler.youtube_crawler.core.dto.JobDto;
import com.crawler.youtube_crawler.core.dto.ResultDto;
import com.crawler.youtube_crawler.core.repository.JobRepository;
import com.crawler.youtube_crawler.core.repository.ResultRepository;
import com.crawler.youtube_crawler.rest_api.producer.JobSender;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class JobService {
    private final JobRepository jobRepository;
    private final ResultRepository resultRepository;
    private final JobSender jobSender;

    public final JobDto createJob(final JobDto jobDto) {
        final JobDto createdJob = new JobDto();
        createdJob.setStatus(JobStatus.NEW);
        jobRepository.save(createdJob);

        try {
            jobSender.sendJob(createdJob);
        }
        catch (Exception e){
            throw new RuntimeException(String.format("Failed to send message to Jms queue. Root cause: %s", e.getMessage()));
        }

        return createdJob;
    }

    public final List<ResultDto> getResults(final Long jobId) {
        return resultRepository.findByJobId(jobId);
    }

    public final JobDto getJob(final Long jobId) {
        return jobRepository.findOne(jobId);
    }
}
