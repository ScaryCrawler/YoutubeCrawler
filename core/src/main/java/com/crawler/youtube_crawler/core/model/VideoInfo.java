package com.crawler.youtube_crawler.core.model;

import com.google.api.services.youtube.model.Comment;
import com.google.api.services.youtube.model.Video;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter
public class VideoInfo {
    //todo: fix MappingInstantiationException for google.DateTime

//    private List<Video> videoDetails;
    private List<String> videoDetails;

//    private List<Comment> comments;
    private List<String> comments;
}
