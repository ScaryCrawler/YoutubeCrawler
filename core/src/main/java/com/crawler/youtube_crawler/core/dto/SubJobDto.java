package com.crawler.youtube_crawler.core.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class SubJobDto implements Serializable {
    private Long id;
    private JobDto job;
}
