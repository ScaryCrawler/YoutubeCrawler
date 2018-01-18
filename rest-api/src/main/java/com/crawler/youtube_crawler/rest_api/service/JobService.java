package com.crawler.youtube_crawler.rest_api.service;

import com.crawler.youtube_crawler.core.constants.JobStatus;
import com.crawler.youtube_crawler.core.dto.AdditionalInfo;
import com.crawler.youtube_crawler.core.dto.JobDto;
import com.crawler.youtube_crawler.core.dto.ResultDto;
import com.crawler.youtube_crawler.core.repository.ResultRepository;
import com.crawler.youtube_crawler.rest_api.producer.JobSender;
import com.crawler.youtube_crawler.core.repository.JobRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class JobService {
    private final JobRepository jobRepository;
    private final ResultRepository resultRepository;
    private final JobSender jobSender;

    public final JobDto createJob(AdditionalInfo additionalInfo) {
        JobDto createdJob = jobRepository.create(additionalInfo);
        createdJob.setStatus(JobStatus.NEW);
        try {
            jobSender.sendJob(createdJob);
        }
        catch (Exception e){
            throw new RuntimeException(String.format("Failed to send message to Jms queue. Root cause: %s", e.getMessage()));
        }

        return createdJob;
    }

    public final Collection<ResultDto> getResults(final String jobId) {
        return resultRepository.readByJobUuid(UUID.fromString(jobId));
    }

    public final JobDto getJob(final String jobId) {
        return jobRepository.read(UUID.fromString(jobId));
    }
}
