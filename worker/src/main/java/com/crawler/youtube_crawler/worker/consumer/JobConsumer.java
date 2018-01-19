package com.crawler.youtube_crawler.worker.consumer;

import com.crawler.youtube_crawler.core.dto.JobDto;

public interface JobConsumer {
    void consume(JobDto jobDto);
}
