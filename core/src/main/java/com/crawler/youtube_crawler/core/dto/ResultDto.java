package com.crawler.youtube_crawler.core.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class ResultDto {
    private UUID id;
    private UUID jobUuid;
}
