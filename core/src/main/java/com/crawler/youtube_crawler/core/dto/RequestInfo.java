package com.crawler.youtube_crawler.core.dto;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.util.JSON;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class RequestInfo {

    private String text;

    private String videoId;

    private int depth;

    @Override
    public String toString() {
        try {
            return new ObjectMapper().writeValueAsString(this);
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }
}
