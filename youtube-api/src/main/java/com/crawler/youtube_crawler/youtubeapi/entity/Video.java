package com.crawler.youtube_crawler.youtubeapi.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;

@Data
public class Video {
    @Id
    private String id;

    private String jobId;

    private String videoId;

    private String author;

    private String title;

    private String keywords;

    private String description;

    private String likes;

    private String dislikes;

    private String views;

    private String date;
}
