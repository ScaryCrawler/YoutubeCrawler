package com.crawler.youtube_crawler.core.repository;

import com.crawler.youtube_crawler.youtubeapi.entity.Comment;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface CommentRepository extends MongoRepository<Comment, String> {
    List<Comment> findCommentsByJobId(String jobId);
}
