package com.crawler.youtube_crawler.worker.handler;

import com.crawler.youtube_crawler.core.dto.SubJobDto;
import org.springframework.stereotype.Component;

@Component
public class SimpleSubJobHandler implements SubJobHandler {
    //TODO: implement logic of data retrieval
    @Override
    public void accept(SubJobDto jobDto) {
    }
}
