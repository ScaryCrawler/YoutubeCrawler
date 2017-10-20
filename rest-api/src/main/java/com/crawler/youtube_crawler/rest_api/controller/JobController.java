package com.crawler.youtube_crawler.rest_api.controller;

import com.crawler.youtube_crawler.core.dto.JobDto;
import com.crawler.youtube_crawler.rest_api.service.JobService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "job")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
final class JobController {
    private final JobService service;

    @RequestMapping(method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public final JobDto create(@RequestBody JobDto jobDto) {
        return service.createJob(jobDto);
    }
}
