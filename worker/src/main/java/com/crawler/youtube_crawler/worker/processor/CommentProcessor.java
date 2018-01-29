package com.crawler.youtube_crawler.worker.processor;

import com.crawler.youtube_crawler.core.constants.JobStatus;
import com.crawler.youtube_crawler.core.constants.JobType;
import com.crawler.youtube_crawler.core.dto.JobDto;
import com.crawler.youtube_crawler.core.entity.RequestInfo;
import com.crawler.youtube_crawler.core.repository.CommentRepository;
import com.crawler.youtube_crawler.core.repository.JobRepository;
import com.crawler.youtube_crawler.core.repository.RequestInfoRepository;
import com.crawler.youtube_crawler.core.repository.VideoRepository;
import com.crawler.youtube_crawler.youtubeapi.entity.Comment;
import com.crawler.youtube_crawler.youtubeapi.entity.ContinuationInfo;
import com.crawler.youtube_crawler.youtubeapi.entity.Video;
import com.crawler.youtube_crawler.youtubeapi.parser.CommentsParser;
import com.crawler.youtube_crawler.youtubeapi.parser.VideoParser;
import com.crawler.youtube_crawler.youtubeapi.parser.YouTubeParser;
import com.crawler.youtube_crawler.youtubeapi.requesthelper.RequestHelper;
import com.jayway.restassured.response.Response;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;


import java.util.List;


@Component
@RequiredArgsConstructor
@Slf4j
public class CommentProcessor implements Processor {

    private final JobRepository jobRepository;
    private final RequestInfoRepository requestInfoRepository;
    private final CommentRepository commentRepository;
    private final VideoRepository videoRepository;

    private final RequestHelper requestHelper;


    @Override
    public String accept(JobDto job) {
        jobRepository.updateStatus(job, JobStatus.IN_PROGRESS);
        try {
            RequestInfo requestInfo = requestInfoRepository.findOne(job.getId());
            Response response = requestHelper.video(requestInfo.getVideoId());

            YouTubeParser<Video> videoParser = new VideoParser(response);
            Video video = videoParser.parse(requestInfo.getVideoId());
            log.info("Data and info parsed successfully for CommentProcessor");
            videoRepository.save(video);

            ContinuationInfo info = videoParser.parseContinuationData();

            int count = 0;

            while (count < requestInfo.getCommentCount()) {
                response = requestHelper.comments(video.getVideoId(), info);
                YouTubeParser<List<Comment>> commentsParser = new CommentsParser(response);

                List<Comment> comments = commentsParser.parse(video.getVideoId());
                commentRepository.save(comments);

                count += comments.size();

                ContinuationInfo newInfo = commentsParser.parseContinuationData();
                info = mergeContinuation(info, newInfo);
                log.info("Data and info parsed successfully for CommentProcessor");
            }

        } catch (Exception e){
            log.error("COMMENTS: ", e);
            return JobStatus.FAILED;
        }


        return JobStatus.COMPLETED;
    }


    @Override
    public boolean canProcess(JobDto jobDto) {
        return JobType.COMMENT.equals(jobDto.getType());
    }

    private ContinuationInfo mergeContinuation(ContinuationInfo main, ContinuationInfo next) {
        next.setSessionToken(main.getSessionToken());
        next.setCookie(main.getCookie());
        return next;
    }
}
