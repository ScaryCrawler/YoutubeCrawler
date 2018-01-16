package com.crawler.youtube_crawler.worker.handler;

import com.crawler.youtube_crawler.core.constants.JobStatus;
import com.crawler.youtube_crawler.core.constants.JobType;
import com.crawler.youtube_crawler.core.dto.JobDto;
import com.crawler.youtube_crawler.core.repository.JobRepository;
import com.crawler.youtube_crawler.worker.youtubeapi.YouTubeApi;
import com.google.api.client.util.Joiner;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.Video;
import com.google.api.services.youtube.model.VideoListResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Настя on 20.11.2017.
 */
@Component
public class VIdeoDetailsHandler implements  Processor{

    @Autowired
    private JobRepository repo;

    @Autowired
    private YouTubeApi youtube;
    @Value("${youtube.apikey}")
    private String apiKey;
    private List<Video> videoDetailsList;


    @Override
    public String accept(JobDto jobDto) {
        try {
            init();
            ArrayList<String> params = new ArrayList<>();
            params.add("HkaUhxGqS5g");
            getVideosDetails(params); //todo: make possible to set params of search
        } catch (Exception e){
            return JobStatus.FAILED;
        }
        return JobStatus.IN_PROGRESS;
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
