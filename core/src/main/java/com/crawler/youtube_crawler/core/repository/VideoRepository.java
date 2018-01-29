package com.crawler.youtube_crawler.core.repository;

import com.crawler.youtube_crawler.youtubeapi.entity.Video;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface VideoRepository extends MongoRepository<Video, String> {
}
