package com.crawler.youtube_crawler.core.repository;

import com.crawler.youtube_crawler.youtubeapi.entity.Video;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface VideoRepository extends MongoRepository<Video, String> {
    List<Video> findVideosByJobId(String jobId);
}
