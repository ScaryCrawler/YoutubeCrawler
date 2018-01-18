package com.crawler.youtube_crawler.rest_api.service;

import com.crawler.youtube_crawler.core.constants.JobStatus;
import com.crawler.youtube_crawler.core.constants.JobType;
import com.crawler.youtube_crawler.core.dto.RequestInfo;
import com.crawler.youtube_crawler.core.dto.JobDto;
import com.crawler.youtube_crawler.core.model.UserRequest;
import com.crawler.youtube_crawler.core.repository.JobRepository;
import com.crawler.youtube_crawler.core.repository.UserRequestRepository;
import com.crawler.youtube_crawler.rest_api.producer.JobSender;
import com.mongodb.util.JSON;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class JobService {
    private final JobSender jobSender;

    private final JobRepository jobRepository;
    private final UserRequestRepository userRequestRepository;

    public final JobDto createJob(RequestInfo requestInfo) {
        JobDto job = new JobDto(JobStatus.NEW, JobType.VIDEO_ID);
//        job.setAdditionalInfo(JSON.serialize(requestInfo));
        jobRepository.save(job);

        try {
            jobSender.sendJob(job);
        }
        catch (Exception e){
            throw new RuntimeException(String.format("Failed to send message to Jms queue. Root cause: %s", e.getMessage()));
        }

        return job;
    }

    public final UserRequest getResults(final String jobId) {
//        return userRequestRepository.readByJobUuid(UUID.fromString(jobId));
        return null;
    }

    public final JobDto getJob(final String jobId) {
        return jobRepository.findOne(jobId);
    }
}
