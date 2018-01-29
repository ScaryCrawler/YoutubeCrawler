package com.crawler.youtube_crawler.youtubeapi.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;

@Data
public class Comment {
    @Id
    private String id;

    private String videoId;

    private String commentId;

    private String jobId;

    private String author;

    private int likes;

    private String text;

}
