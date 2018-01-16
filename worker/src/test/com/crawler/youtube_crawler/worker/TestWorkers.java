package com.crawler.youtube_crawler.worker;

import com.crawler.youtube_crawler.core.constants.JobStatus;
import com.crawler.youtube_crawler.core.constants.JobType;
import com.crawler.youtube_crawler.core.dto.JobDto;
import com.crawler.youtube_crawler.worker.handler.Handler;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.UUID;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestWorkers {

    @Autowired
    private Handler handler;

    @Test
    public void testVideoIdJob() throws Exception {
        JobDto jobDto = new JobDto();
        jobDto.setId(UUID.randomUUID());
        jobDto.setType(JobType.VIDEO_ID);
        jobDto.setStatus(JobStatus.IN_PROGRESS);

        String status = handler.accept(jobDto);
        Assertions.assertThat(status).isEqualTo(JobStatus.IN_PROGRESS);
    }


}
