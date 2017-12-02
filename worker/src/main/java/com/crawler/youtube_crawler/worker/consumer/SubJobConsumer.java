package com.crawler.youtube_crawler.worker.consumer;

import com.crawler.youtube_crawler.core.dto.SubJobDto;
import com.crawler.youtube_crawler.worker.handler.SubJobHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor
public class SubJobConsumer {
    private final SubJobHandler jobHandler;

    @ServiceActivator(inputChannel = "messageSubJobChannel")
    public final void consumeJob(SubJobDto job) {
        final UUID jobId = job.getId();

        log.info("SubJob " + jobId + " has been taken from queue");

        jobHandler.accept(job);
    }
}
