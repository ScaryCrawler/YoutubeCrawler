package com.crawler.youtube_crawler.core.dto;

import com.crawler.youtube_crawler.core.constants.JobStatus;
import com.crawler.youtube_crawler.core.constants.JobType;
import lombok.*;
import org.springframework.data.annotation.Id;

import java.io.Serializable;


@Getter @Setter
@RequiredArgsConstructor
public class JobDto implements Serializable{
    @Id
    private String id;

    private String parentId;

    @NonNull
    private String status;

    @NonNull
    private String type;

}
