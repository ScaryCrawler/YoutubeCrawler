package com.crawler.youtube_crawler.core.repository;

import com.crawler.youtube_crawler.core.dto.JobDto;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

//TODO: implement using Spring Data/myBatis
public interface JobRepository {
    JobDto create(@NonNull final JobDto job);
    JobDto read(@NonNull final UUID id);
    void setStatus(@NonNull final UUID jobId, @NonNull final String status);
    void delete(@NonNull final UUID id);
}