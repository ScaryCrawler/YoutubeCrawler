package com.crawler.youtube_crawler.core.repository;

import com.crawler.youtube_crawler.core.entity.RequestInfo;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface RequestInfoRepository extends MongoRepository<RequestInfo, String> {
}
