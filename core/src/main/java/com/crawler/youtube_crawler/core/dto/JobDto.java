package com.crawler.youtube_crawler.core.dto;

import com.crawler.youtube_crawler.core.constants.JobStatus;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.Value;

import javax.validation.constraints.AssertTrue;
import java.io.Serializable;
import java.util.Arrays;
import java.util.UUID;

@Data
public class JobDto implements Serializable{
    private UUID id;
    private String status;
    private String type;

    private AdditionalInfo additionalInfo;
}
