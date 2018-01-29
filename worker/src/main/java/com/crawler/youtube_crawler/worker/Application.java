package com.crawler.youtube_crawler.worker;

import com.crawler.youtube_crawler.core.entity.RequestInfo;
import com.crawler.youtube_crawler.youtubeapi.entity.Comment;
import com.crawler.youtube_crawler.youtubeapi.entity.ContinuationInfo;
import com.crawler.youtube_crawler.youtubeapi.entity.Video;
import com.crawler.youtube_crawler.youtubeapi.parser.CommentsParser;
import com.crawler.youtube_crawler.youtubeapi.parser.VideoParser;
import com.crawler.youtube_crawler.youtubeapi.parser.YouTubeParser;
import com.crawler.youtube_crawler.youtubeapi.proxy.CircleDelayProxyGenerator;
import com.crawler.youtube_crawler.youtubeapi.requesthelper.RequestHelper;
import com.crawler.youtube_crawler.youtubeapi.requesthelper.YouTubeRequestHelper;
import com.jayway.restassured.response.Response;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.scheduling.annotation.EnableAsync;

import java.util.List;

@SpringBootApplication
@ComponentScan("com.crawler.youtube_crawler.*")
@EnableAsync
@EnableMongoRepositories(basePackages = "com.crawler.youtube_crawler.*")
public class Application {

    public static void main(final String[] arguments) throws Exception {
       ApplicationContext context = SpringApplication.run(Application.class, arguments);
    }



}
