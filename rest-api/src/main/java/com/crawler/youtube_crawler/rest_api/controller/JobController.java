package com.crawler.youtube_crawler.rest_api.controller;

import com.crawler.youtube_crawler.core.dto.AdditionalInfo;
import com.crawler.youtube_crawler.core.dto.JobDto;
import com.crawler.youtube_crawler.core.dto.ResultDto;
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
    public final JobDto create(@RequestBody AdditionalInfo additionalInfo) {
        return service.createJob(additionalInfo);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/results")
    @ResponseStatus(HttpStatus.OK)
    public final Collection<ResultDto> getResult(@RequestBody String jobId) {
        return service.getResults(jobId);
    }

    @RequestMapping(method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public final JobDto get(@RequestBody String jobId) {
        return service.getJob(jobId);
    }
}
