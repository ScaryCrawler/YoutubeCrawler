package com.crawler.youtube_crawler.rest_api.service;

import com.crawler.youtube_crawler.core.constants.JobStatus;
import com.crawler.youtube_crawler.core.constants.JobType;
import com.crawler.youtube_crawler.core.entity.RequestInfo;
import com.crawler.youtube_crawler.core.dto.JobDto;
import com.crawler.youtube_crawler.core.repository.JobRepository;
import com.crawler.youtube_crawler.core.repository.RequestInfoRepository;
import com.crawler.youtube_crawler.rest_api.producer.JobSender;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Collection;

@Service
@RequiredArgsConstructor
public class JobService {
    private final JobSender jobSender;

    private final JobRepository jobRepository;
    private final RequestInfoRepository requestInfoRepository;

    public final JobDto createJob(String query, int videoCount, int commentsCount) {
        JobDto job = new JobDto(JobStatus.NEW, JobType.VIDEO_ID);

        RequestInfo requestInfo = new RequestInfo();
        requestInfo.setText(query);
        requestInfo.setVideoCount(videoCount);
        requestInfo.setCommentCount(commentsCount);
        jobRepository.save(job);

        requestInfo.setId(job.getId());
        requestInfoRepository.save(requestInfo);

        try {
            jobSender.sendJob(job);
        }
        catch (Exception e){
            throw new RuntimeException(String.format("Failed to send message to Jms queue. Root cause: %s", e.getMessage()));
        }

        return job;
    }

    public final Collection<?> getResults() {
        return null;
    }

    public final JobDto getJob(final String jobId) {
        return jobRepository.findOne(jobId);
    }
}
