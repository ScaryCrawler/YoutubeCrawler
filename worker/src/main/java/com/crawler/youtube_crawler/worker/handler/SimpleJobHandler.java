package com.crawler.youtube_crawler.worker.handler;

import com.crawler.youtube_crawler.core.constants.JobStatus;
import com.crawler.youtube_crawler.core.dto.JobDto;
import com.crawler.youtube_crawler.core.dto.SubJobDto;
import com.crawler.youtube_crawler.worker.producer.SubJobSender;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SimpleJobHandler implements JobHandler {
    private final SubJobSender subJobSender;

    //TODO: implement logic of data retrieval
    @Override
    public String accept(JobDto jobDto) {
        final SubJobDto subjob = new SubJobDto();
        subjob.setJobId(jobDto.getId());
        subJobSender.sendSubJob(subjob);
        return JobStatus.FAILED;
    }
}
