package com.crawler.youtube_crawler.core.repository;

import com.crawler.youtube_crawler.youtubeapi.entity.Comment;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CommentRepository extends MongoRepository<Comment, String> {
}
