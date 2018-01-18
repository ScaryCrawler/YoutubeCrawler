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

    /*
    * Field for additional info.
    * if type is VIDEO_ID then it contains user request and depth of search as json string
    * for other types it contains video id as number
    * */
    private String additionalInfo;

}
