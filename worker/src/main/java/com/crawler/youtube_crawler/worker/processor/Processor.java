package com.crawler.youtube_crawler.worker.processor;

import com.crawler.youtube_crawler.core.dto.JobDto;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public interface Processor {

    String accept(JobDto jobDto);

    boolean canProcess(JobDto jobDto);
}
