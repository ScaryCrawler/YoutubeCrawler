package com.crawler.youtube_crawler.worker.handler;

import com.crawler.youtube_crawler.core.constants.JobStatus;
import com.crawler.youtube_crawler.core.constants.JobType;
import com.crawler.youtube_crawler.core.dto.JobDto;
import com.crawler.youtube_crawler.core.repository.JobRepository;
import com.crawler.youtube_crawler.worker.youtubeapi.YouTubeApi;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.SearchListResponse;
import com.google.api.services.youtube.model.SearchResult;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
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
@RequiredArgsConstructor
public class VideoIdHandler implements Processor {

    private final JobRepository jobRepository;

    private static long NUMBER_OF_VIDEOS_RETURNED = 50;

    private final YouTubeApi youtube;

//    private final JobSender jobSender;

    @Value("${youtube.apikey}")
    private String apiKey;

    private YouTube.Search.List search;

    private List<String> videoIdsList = new ArrayList<>();

    @Override
    public String accept(JobDto jobDto) {
       try {
           init();
           executeSearch(jobDto.getAdditionalInfo().getRequest(), jobDto.getAdditionalInfo().getDepth());
           videoIdsList.forEach(id -> {
//todo: implement logic for sending task with producer
//               JobDto createdJob = jobRepository.create(jobDto.getAdditionalInfo());
//               createdJob.setType(JobType.COMMENT);
//               jobSender.sendJob(createdJob);
//
//               createdJob = jobRepository.create(jobDto.getAdditionalInfo());
//               createdJob.setType(JobType.VIDEO_DETAILS);
//               jobSender.sendJob(createdJob);
           });
       } catch (Exception e){
           return JobStatus.FAILED;
       }
        return JobStatus.IN_PROGRESS;
    }

    @Override
    public boolean canProcess(JobDto jobDto) {
        return JobType.VIDEO_ID.equals(jobDto.getType());
    }

    private void init() {
        try {
            this.search = this.youtube.getYouTube().search().list("id");
            search.setKey(apiKey);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void executeSearch(String query, int recursionDepth) throws IOException {
        search.setType("video").setMaxResults(NUMBER_OF_VIDEOS_RETURNED).setQ(query);
        boolean allResultsRead = false;
        while (!allResultsRead) {
            SearchListResponse searchResponse = search.execute();
            for (SearchResult res : searchResponse.getItems()) {
                videoIdsList.add(res.getId().getVideoId());
            }

            String nextPageToken = searchResponse.getNextPageToken();

            if (nextPageToken == null) {
                allResultsRead = true;
                search.setType("video").setMaxResults(NUMBER_OF_VIDEOS_RETURNED).setQ(query);
            } else {
                search.setPageToken(nextPageToken);
            }
        }

        List<String> currentLevelVideosIds = new ArrayList<>(videoIdsList);

        for (int i = 0; i < recursionDepth; i++) {
            List<String> relatedVideosIds = new ArrayList<>();
            for (String videoId : currentLevelVideosIds) {
                for (SearchResult res : getRelatedVideo(videoId)) {
                    relatedVideosIds.add(res.getId().getVideoId());
                }
            }
            videoIdsList.addAll(relatedVideosIds);
            currentLevelVideosIds = new ArrayList<>(relatedVideosIds);
        }
    }

    private List<SearchResult> getRelatedVideo(String videoId) throws IOException {
        search.setRelatedToVideoId(videoId)
                .setMaxResults(10L) //todo: replace 10 with totalReplyCount
                .setType("video")
                .setPart("id")
                .setOrder("viewCount");
        SearchListResponse searchResponse = search.execute();
        return searchResponse.getItems();
    }

}
