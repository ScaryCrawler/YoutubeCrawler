package com.crawler.youtube_crawler.core.model;

import com.google.api.services.youtube.model.Comment;
import com.google.api.services.youtube.model.Video;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter
public class VideoInfo {
    private List<Video> videoDetails;

    private List<Comment> comments;
}
