package com.crawler.youtube_crawler.worker.processor;

import com.crawler.youtube_crawler.core.JobUtils;
import com.crawler.youtube_crawler.core.constants.JobStatus;
import com.crawler.youtube_crawler.core.constants.JobType;
import com.crawler.youtube_crawler.core.dto.JobDto;
import com.crawler.youtube_crawler.core.repository.JobRepository;
import com.crawler.youtube_crawler.core.repository.UserRequestRepository;
import com.crawler.youtube_crawler.worker.youtubeapi.YouTubeApi;
import com.google.api.client.util.Joiner;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.Video;
import com.google.api.services.youtube.model.VideoListResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Настя on 20.11.2017.
 */
@Component
@RequiredArgsConstructor
public class VideoDetailsProcessor implements  Processor{

    private final JobRepository jobRepository;
    private final UserRequestRepository userRequestRepository;

    private final YouTubeApi youtube;


    @Value("${youtube.apikey}")
    private String apiKey;

    private List<Video> videoDetailsList;


    @Override
    public String accept(JobDto jobDto) {
        jobRepository.updateStatus(jobDto, JobStatus.IN_PROGRESS);
        try {
            init();
            List<String> params = Arrays.asList(JobUtils.extractRequestInfo(jobDto).getVideoId());
            getVideosDetails(params);
            userRequestRepository.updateVideoDetails(jobDto.getParentId(), JobUtils.extractRequestInfo(jobDto).getVideoId(),
                    Arrays.asList((String[])videoDetailsList.stream().map(video -> video.toString()).toArray()));
        } catch (Exception e){
            return JobStatus.FAILED;
        }
        return JobStatus.COMPLETED;
    }

    private void init(){

    }

    private void getVideosDetails(List videoIds) throws IOException {
        Joiner stringJoiner = Joiner.on(',');
        String videoId = stringJoiner.join(videoIds);

        YouTube.Videos.List listVideosRequest = youtube.getYouTube().videos()
                .list("id,  statistics, snippet ")
                .setId(videoId)
                .setKey(apiKey);
        VideoListResponse listResponse = listVideosRequest.execute();

        videoDetailsList = listResponse.getItems();
    }

    @Override
    public boolean canProcess(JobDto jobDto) {
        return JobType.VIDEO_DETAILS.equals(jobDto.getType());
    }



}
