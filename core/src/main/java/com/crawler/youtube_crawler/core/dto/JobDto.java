package com.crawler.youtube_crawler.core.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.Value;

import java.io.Serializable;
import java.util.UUID;

@Data
@Setter
@Getter
public class JobDto implements Serializable{
    private UUID id;
}
