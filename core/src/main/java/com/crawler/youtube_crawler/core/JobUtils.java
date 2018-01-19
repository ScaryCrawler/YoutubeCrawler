package com.crawler.youtube_crawler.core;

import com.crawler.youtube_crawler.core.dto.JobDto;
import com.crawler.youtube_crawler.core.dto.RequestInfo;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JobUtils {
    private static final ObjectMapper MAPPER = new ObjectMapper();

    public static RequestInfo extractRequestInfo(JobDto job) {
        try {
            return MAPPER.readValue(job.getAdditionalInfo(), RequestInfo.class);
        } catch (Exception e) {
            throw new RuntimeException("Not true format for additional info");
        }

    }

}

