package com.crawler.youtube_crawler.rest_api.service;

import com.crawler.youtube_crawler.core.constants.JobStatus;
import com.crawler.youtube_crawler.core.constants.JobType;
import com.crawler.youtube_crawler.core.entity.RequestInfo;
import com.crawler.youtube_crawler.core.dto.JobDto;
import com.crawler.youtube_crawler.core.repository.CommentRepository;
import com.crawler.youtube_crawler.core.repository.JobRepository;
import com.crawler.youtube_crawler.core.repository.RequestInfoRepository;
import com.crawler.youtube_crawler.core.repository.VideoRepository;
import com.crawler.youtube_crawler.rest_api.producer.JobSender;
import com.crawler.youtube_crawler.youtubeapi.entity.Comment;
import com.crawler.youtube_crawler.youtubeapi.entity.Video;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import javax.swing.text.html.Option;
import java.util.Collection;
import java.util.List;

@Service
@RequiredArgsConstructor
public class JobService {
    private final JobSender jobSender;

    private final JobRepository jobRepository;
    private final RequestInfoRepository requestInfoRepository;
    private final CommentRepository commentRepository;
    private final VideoRepository videoRepository;

    public final JobDto createJob(String query, int videoCount, int commentsCount) {
        JobDto job = new JobDto(JobStatus.NEW, JobType.VIDEO_ID);

        RequestInfo requestInfo = new RequestInfo();
        requestInfo.setText(query);
        requestInfo.setVideoCount(videoCount);
        requestInfo.setCommentCount(commentsCount);
        jobRepository.save(job);

        requestInfo.setId(job.getId());
        requestInfoRepository.save(requestInfo);

        try {
            jobSender.sendJob(job);
        }
        catch (Exception e){
            throw new RuntimeException(String.format("Failed to send message to Jms queue. Root cause: %s", e.getMessage()));
        }

        return job;
    }



    public final List<Comment> getComments(String jobId){ return commentRepository.findCommentsByJobId(jobId);}

    public final List<Video> getVideos(String jobId){ return videoRepository.findVideosByJobId(jobId);}

    public final JobDto getJob(final String jobId) {
        return jobRepository.findOne(jobId);
    }
}
