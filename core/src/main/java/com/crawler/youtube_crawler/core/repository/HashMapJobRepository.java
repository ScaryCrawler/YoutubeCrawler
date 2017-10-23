package com.crawler.youtube_crawler.core.repository;

import com.crawler.youtube_crawler.core.dto.JobDto;
import lombok.NonNull;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class HashMapJobRepository implements JobRepository {
    private Map<UUID, JobDto> parameterJobMap = new HashMap<>();

    @Override
    public JobDto read(@NonNull final UUID id) {
        return parameterJobMap.get(id);
    }

    @Override
    public void setStatus(@NonNull UUID jobId, @NonNull String status) {
        final JobDto job = parameterJobMap.get(jobId);
        if (!Optional.ofNullable(job).isPresent())
            throw new NoSuchElementException(String.format("Job repository does not contain job %s", jobId));

        job.setStatus(status);
    }

    @Override
    public JobDto create(@NonNull final JobDto job) {
        job.setId(UUID.randomUUID());
        parameterJobMap.put(job.getId(), job);
        return job;
    }

    @Override
    public void delete(@NonNull final UUID id) {
        parameterJobMap.remove(id);
    }
}