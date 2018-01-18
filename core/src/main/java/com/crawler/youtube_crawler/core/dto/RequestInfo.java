package com.crawler.youtube_crawler.core.dto;

import com.mongodb.util.JSON;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class RequestInfo {

    private String text;

    private int depth;
}
