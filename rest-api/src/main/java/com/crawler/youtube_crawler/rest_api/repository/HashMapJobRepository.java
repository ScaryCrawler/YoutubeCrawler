package com.crawler.youtube_crawler.rest_api.repository;

import com.crawler.youtube_crawler.core.dto.JobDto;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Component
public class HashMapJobRepository implements JobRepository {
    private Map<UUID, JobDto> parameterJobMap;

    public HashMapJobRepository() {
        parameterJobMap = new HashMap<>();
    }

    @Override
    public JobDto findOne(final UUID id) {
        return parameterJobMap.get(id);
    }

    @Override
    public JobDto save(final JobDto job) {
        job.setId(UUID.randomUUID());
        parameterJobMap.put(job.getId(), job);
        return job;
    }

    @Override
    public void delete(final UUID id) {
        parameterJobMap.remove(id);
    }
}