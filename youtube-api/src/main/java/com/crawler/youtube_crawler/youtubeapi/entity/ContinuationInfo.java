package com.crawler.youtube_crawler.youtubeapi.entity;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ContinuationInfo {

    private String ctoken;

    private String continuation;

    private String sessionToken;

    private String itct;

    private String cookie;
}
