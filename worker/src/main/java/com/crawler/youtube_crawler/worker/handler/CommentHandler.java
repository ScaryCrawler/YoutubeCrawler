package com.crawler.youtube_crawler.worker.handler;

import com.crawler.youtube_crawler.core.constants.JobStatus;
import com.crawler.youtube_crawler.core.constants.JobType;
import com.crawler.youtube_crawler.core.dto.JobDto;
import com.crawler.youtube_crawler.core.repository.JobRepository;
import com.crawler.youtube_crawler.worker.youtubeapi.YouTubeApi;
import com.google.api.services.youtube.model.Comment;
import com.google.api.services.youtube.model.CommentListResponse;
import com.google.api.services.youtube.model.CommentThread;
import com.google.api.services.youtube.model.CommentThreadListResponse;
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
public class CommentHandler implements Processor {

    @Autowired
    private JobRepository repo;
    @Autowired
    private YouTubeApi youtube;
    @Value("${youtube.apikey}")
    private String apiKey;
    private List<Comment> commentsList;

    @Override
    public String accept(JobDto jobDto) {
        try {
            init();
            getVideoComments("HkaUhxGqS5g" ); //todo: make possible to set params of search
        } catch (Exception e){
            return JobStatus.FAILED;
        }
        return JobStatus.IN_PROGRESS;
    }

    private void init(){

    }

    private void getVideoComments(String videoId) throws IOException {
        commentsList = new ArrayList<>();

        CommentThreadListResponse videoCommentsListResponse = youtube.getYouTube().commentThreads()
                .list("snippet")
                .setVideoId(videoId)
                .setTextFormat("plainText")
                .setMaxResults(Long.parseLong("100"))
                .setKey(apiKey)
                .execute();
        for (CommentThread commentTread: videoCommentsListResponse.getItems()){
            commentsList.add(commentTread.getSnippet().getTopLevelComment());
        }
        String nextPageToken = videoCommentsListResponse.getNextPageToken();

        boolean allResultsRead = false;
        while (!allResultsRead) {

            try {
                videoCommentsListResponse = youtube.getYouTube().commentThreads()
                        .list("snippet")
                        .setVideoId(videoId)
                        .setTextFormat("plainText")
                        .setMaxResults(Long.parseLong("100"))
                        .setKey(apiKey)
                        .setPageToken(nextPageToken)
                        .execute();

                for (CommentThread commentTread: videoCommentsListResponse.getItems()){
                    commentsList.add(commentTread.getSnippet().getTopLevelComment());
                }

                nextPageToken = videoCommentsListResponse.getNextPageToken();
                for (Comment comment : commentsList) {
                    commentsList.addAll(getRepliesToComment(comment.getId()));
                }
            } catch (Exception e) {
                System.out.println(e.getLocalizedMessage());
            }

            if (nextPageToken == null) {
                allResultsRead = true;
            }
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

}
