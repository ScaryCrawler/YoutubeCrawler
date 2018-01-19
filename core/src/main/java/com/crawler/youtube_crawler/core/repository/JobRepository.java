package com.crawler.youtube_crawler.core.repository;

import com.crawler.youtube_crawler.core.dto.JobDto;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface JobRepository extends MongoRepository<JobDto, String> {

    default JobDto updateStatus(JobDto jobDto, String status){
        jobDto.setStatus(status);
        return save(jobDto);
    }

}
