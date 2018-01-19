package com.crawler.youtube_crawler.worker.processor;

import com.crawler.youtube_crawler.core.JobUtils;
import com.crawler.youtube_crawler.core.constants.JobStatus;
import com.crawler.youtube_crawler.core.constants.JobType;
import com.crawler.youtube_crawler.core.dto.JobDto;
import com.crawler.youtube_crawler.core.dto.RequestInfo;
import com.crawler.youtube_crawler.core.model.UserRequest;
import com.crawler.youtube_crawler.core.repository.JobRepository;
import com.crawler.youtube_crawler.core.repository.UserRequestRepository;
import com.crawler.youtube_crawler.worker.producer.JobSender;
import com.crawler.youtube_crawler.worker.youtubeapi.YouTubeApi;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.SearchListResponse;
import com.google.api.services.youtube.model.SearchResult;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


/**
 * Created by Настя on 20.11.2017.
 */
@Component
@RequiredArgsConstructor
public class VideoIdProcessor implements Processor {

    @Value("${youtube.videocount:1}")
    private long numberOfVideosReturned;

    private final YouTubeApi youtube;

    private final JobSender jobSender;

    @Value("${youtube.apikey}")
    private String apiKey;

    private YouTube.Search.List search;

    private List<String> videoIdsList = new ArrayList<>();

    private final JobRepository jobRepository;
    private final UserRequestRepository userRequestRepository;

    @Override
    public String accept(JobDto jobDto) {
        jobRepository.updateStatus(jobDto, JobStatus.IN_PROGRESS);
       try {
           init();
           RequestInfo requestInfo = JobUtils.extractRequestInfo(jobDto);
           executeSearch(requestInfo.getText(), requestInfo.getDepth());

           UserRequest userRequest = new UserRequest();
           userRequest.setRequestText(requestInfo.getText());
           userRequest.setVideos(new HashMap<>());
           userRequestRepository.save(userRequest);

           videoIdsList.forEach(videoId -> {
               sendJob(userRequest.getId(), videoId, JobType.COMMENT);
               sendJob(userRequest.getId(), videoId, JobType.VIDEO_DETAILS);
           });
       } catch (Exception e){
           return JobStatus.FAILED;
       }
        return JobStatus.COMPLETED;
    }

    private void sendJob(String parentId, String videoId, String jobType){
        JobDto job = new JobDto(JobStatus.NEW, jobType);

        RequestInfo requestInfo = new RequestInfo();
        requestInfo.setVideoId(videoId);
        job.setAdditionalInfo(requestInfo.toString());

        job.setParentId(parentId);
        jobRepository.save(job);
        jobSender.sendJob(job);
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
        search.setType("video").setMaxResults(numberOfVideosReturned).setQ(query);
        boolean allResultsRead = false;
        for (int i = 0; i < numberOfVideosReturned && !allResultsRead; i++) {
            SearchListResponse searchResponse = search.execute();
            for (SearchResult res : searchResponse.getItems()) {
                videoIdsList.add(res.getId().getVideoId());
            }

            String nextPageToken = searchResponse.getNextPageToken();

            if (nextPageToken == null) {
                allResultsRead = true;
                search.setType("video").setMaxResults(numberOfVideosReturned).setQ(query);
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
                .setMaxResults(numberOfVideosReturned)
                .setType("video")
                .setPart("id")
                .setOrder("viewCount");
        SearchListResponse searchResponse = search.execute();
        return searchResponse.getItems();
    }

}
