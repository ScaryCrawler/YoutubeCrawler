package com.crawler.youtube_crawler.rest_api.repository;

import com.crawler.youtube_crawler.core.dto.JobDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public interface JobRepository {
    JobDto findOne(final UUID id);

    JobDto save(final JobDto job);

    void delete(final UUID id);
}