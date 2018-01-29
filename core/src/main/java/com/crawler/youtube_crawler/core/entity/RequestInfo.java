package com.crawler.youtube_crawler.core.entity;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.util.JSON;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;

@Getter @Setter
public class RequestInfo {
    @Id
    private String id;

    private String text;

    private String videoId;

    private int videoCount;

    private int commentCount;

    @Override
    public String toString() {
        try {
            return new ObjectMapper().writeValueAsString(this);
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }
}
