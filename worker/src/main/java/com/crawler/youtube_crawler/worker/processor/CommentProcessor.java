package com.crawler.youtube_crawler.worker.processor;

import com.crawler.youtube_crawler.core.JobUtils;
import com.crawler.youtube_crawler.core.constants.JobStatus;
import com.crawler.youtube_crawler.core.constants.JobType;
import com.crawler.youtube_crawler.core.dto.JobDto;
import com.crawler.youtube_crawler.core.repository.JobRepository;
import com.crawler.youtube_crawler.core.repository.UserRequestRepository;
import com.crawler.youtube_crawler.worker.youtubeapi.YouTubeApi;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.Comment;
import com.google.api.services.youtube.model.CommentListResponse;
import com.google.api.services.youtube.model.CommentThread;
import com.google.api.services.youtube.model.CommentThreadListResponse;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Настя on 20.11.2017.
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class CommentProcessor implements Processor {

    private final JobRepository jobRepository;
    private final UserRequestRepository userRequestRepository;

    private final YouTubeApi youtube;


    @Value("${youtube.apikey}")
    private String apiKey;

    @Getter
    private List<Comment> commentsList;

    @Override
    public String accept(JobDto jobDto) {
        jobRepository.updateStatus(jobDto, JobStatus.IN_PROGRESS);
        try {
            init();
            getVideoComments(JobUtils.extractRequestInfo(jobDto).getVideoId());
            userRequestRepository.updateComments(jobDto.getParentId(), JobUtils.extractRequestInfo(jobDto).getVideoId(),
                    Arrays.asList((String[])commentsList.stream().map(comment -> comment.toString()).toArray()));
        } catch (Exception e){
            return JobStatus.FAILED;
        }
        return JobStatus.COMPLETED;
    }

    private void init(){
    }

    private void getVideoComments(String videoId) throws IOException {
        commentsList = new ArrayList<>();

        //todo: for each text is different only next pageToken. You can create one text and to change pageToken
        CommentThreadListResponse videoCommentsListResponse = commentRequestConfig(videoId).execute();
        addTopLevelComments(videoCommentsListResponse);

        String nextPageToken = videoCommentsListResponse.getNextPageToken();

        while (nextPageToken != null) {
            try {
                videoCommentsListResponse = commentRequestConfig(videoId)
                        .setPageToken(nextPageToken)
                        .execute();

                addTopLevelComments(videoCommentsListResponse);
                addRepliesComments();

                nextPageToken = videoCommentsListResponse.getNextPageToken();
            } catch (Exception e) {
                log.error(e.getLocalizedMessage());
            }
        }
    }

    private void addRepliesComments() throws IOException {
        for (Comment comment : commentsList) {
            commentsList.addAll(getRepliesToComment(comment.getId()));
        }
    }


    private List<Comment> getRepliesToComment(String commentId) throws IOException {
        CommentListResponse commentsListResponse = youtube.getYouTube().comments().list("id, snippet")
                .setParentId(commentId).setTextFormat("plainText").execute();
        return  commentsListResponse.getItems();
    }

    @Override
    public boolean canProcess(JobDto jobDto) {
        return JobType.COMMENT.equals(jobDto.getType());
    }

    private YouTube.CommentThreads.List commentRequestConfig(String videoId) throws IOException {
        return youtube.getYouTube().commentThreads()
                .list("snippet")
                .setVideoId(videoId)
                .setTextFormat("plainText")
                .setMaxResults(100L)
                .setKey(apiKey);
    }

    private void addTopLevelComments(CommentThreadListResponse videoCommentsListResponse) {
        for (CommentThread commentTread: videoCommentsListResponse.getItems()){
            commentsList.add(commentTread.getSnippet().getTopLevelComment());
        }
    }
}