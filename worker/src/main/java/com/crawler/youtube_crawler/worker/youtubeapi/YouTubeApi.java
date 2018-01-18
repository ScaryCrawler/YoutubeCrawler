package com.crawler.youtube_crawler.worker.youtubeapi;


import com.google.api.services.youtube.YouTube;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Getter
@RequiredArgsConstructor
public class YouTubeApi {
    private final YouTube youTube;
}
