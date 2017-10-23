package com.crawler.youtube_crawler.worker.handler;

import com.crawler.youtube_crawler.core.constants.JobStatus;
import com.crawler.youtube_crawler.core.dto.JobDto;
import org.springframework.stereotype.Component;

@Component
public class SimpleHandler implements Handler {
    //TODO: implement logic of data retrieval
    @Override
    public String accept(JobDto jobDto) {
        return JobStatus.FAILED;
    }
}
