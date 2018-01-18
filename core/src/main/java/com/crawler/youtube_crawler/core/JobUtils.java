package com.crawler.youtube_crawler.core;

import com.crawler.youtube_crawler.core.constants.JobType;
import com.crawler.youtube_crawler.core.dto.JobDto;
import com.crawler.youtube_crawler.core.dto.RequestInfo;
import com.mongodb.util.JSON;

public class JobUtils {

    public static String extractVideoId(JobDto job) {
        if (job.getAdditionalInfo().matches("^\\d+$")) throw new RuntimeException("Not true format for additional info");
        return job.getAdditionalInfo();
    }

    public static RequestInfo extractRequestInfo(JobDto job) {
        if (!job.getAdditionalInfo().matches("^\\d+$")) throw new RuntimeException("Not true format for additional info");


        return (RequestInfo)JSON.parse(job.getAdditionalInfo());

    }
}

