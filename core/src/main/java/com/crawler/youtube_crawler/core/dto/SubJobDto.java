package com.crawler.youtube_crawler.core.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.UUID;

@Data
@Setter
@Getter
public class SubJobDto implements Serializable {
    private UUID id;
    private UUID jobId;
}
