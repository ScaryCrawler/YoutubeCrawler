package com.crawler.youtube_crawler.worker.producer;

import com.crawler.youtube_crawler.core.dto.JobDto;

public interface JobSender {
    void sendJob(JobDto jobDto);
}
