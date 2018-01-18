package com.crawler.youtube_crawler.core.model;

import java.util.Map;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import org.springframework.data.annotation.Id;

@Getter @Setter
public class UserRequest {
    @Id @NonNull
    private String id;

    private String requestText;

    private Map<String, VideoInfo> videos;
}
