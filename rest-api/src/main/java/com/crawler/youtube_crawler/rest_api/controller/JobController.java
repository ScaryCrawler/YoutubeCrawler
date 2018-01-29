package com.crawler.youtube_crawler.rest_api.controller;

import com.crawler.youtube_crawler.core.entity.RequestInfo;
import com.crawler.youtube_crawler.core.dto.JobDto;
import com.crawler.youtube_crawler.rest_api.service.JobService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping(value = "job")
@RequiredArgsConstructor
final class JobController {
    private final JobService service;


    @RequestMapping(method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public final JobDto create(@RequestParam String query, @RequestParam int videoCount, @RequestParam int commentsCount) {
        return service.createJob(query, videoCount, commentsCount);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/results")
    @ResponseStatus(HttpStatus.OK)
    public final Collection<?> getResult() {
        return service.getResults();
    }

    @RequestMapping(method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public final JobDto get(@RequestBody String jobId) {
        return service.getJob(jobId);
    }


}
