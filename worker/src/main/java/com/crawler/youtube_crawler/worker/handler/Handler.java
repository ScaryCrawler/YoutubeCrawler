package com.crawler.youtube_crawler.worker.handler;

import com.crawler.youtube_crawler.core.dto.JobDto;

//TODO: in case we decide to have several types of handlers, please use Factory template, returning instance of Handler of certain type, depending on the job type
public interface Handler {
    public String accept(JobDto jobDto);
}
