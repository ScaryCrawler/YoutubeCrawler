package com.crawler.youtube_crawler.core.repository;

import com.crawler.youtube_crawler.core.dto.JobDto;
import com.crawler.youtube_crawler.core.dto.ResultDto;
import lombok.NonNull;
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.Query;
//import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

//@Repository
public interface ResultRepository /*extends JpaRepository<ResultDto, Long>*/ {
    List<ResultDto> findByJob(JobDto job);

//    @Query("FROM ResultDto r WHERE r.job.id = :jobId")
    List<ResultDto> findByJobId(/*@Param("jobId")*/Long jobId);
}