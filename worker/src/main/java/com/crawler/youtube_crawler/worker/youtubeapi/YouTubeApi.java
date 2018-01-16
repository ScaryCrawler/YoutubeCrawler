package com.crawler.youtube_crawler.worker.youtubeapi;


import com.google.api.services.youtube.YouTube;
import lombok.Getter;
import org.springframework.stereotype.Service;

@Service
@Getter
public class YouTubeApi {
    private final YouTube youTube = new YouTube.Builder(Auth.HTTP_TRANSPORT, Auth.JSON_FACTORY, request -> {
    }).setApplicationName("youtube-search").build();
}
