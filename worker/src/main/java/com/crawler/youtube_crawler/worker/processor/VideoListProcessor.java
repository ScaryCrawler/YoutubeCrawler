package com.crawler.youtube_crawler.worker.processor;

import com.crawler.youtube_crawler.core.constants.JobStatus;
import com.crawler.youtube_crawler.core.constants.JobType;
import com.crawler.youtube_crawler.core.dto.JobDto;
import com.crawler.youtube_crawler.core.entity.RequestInfo;
import com.crawler.youtube_crawler.core.repository.JobRepository;
import com.crawler.youtube_crawler.core.repository.RequestInfoRepository;
import com.crawler.youtube_crawler.worker.producer.JobSender;
import com.crawler.youtube_crawler.youtubeapi.entity.ContinuationInfo;
import com.crawler.youtube_crawler.youtubeapi.parser.FirstResponseVideoListParser;
import com.crawler.youtube_crawler.youtubeapi.parser.NextResponseVideoListParser;
import com.crawler.youtube_crawler.youtubeapi.parser.YouTubeParser;
import com.crawler.youtube_crawler.youtubeapi.requesthelper.RequestHelper;
import com.crawler.youtube_crawler.youtubeapi.requesthelper.YouTubeRequestHelper;
import com.jayway.restassured.response.Response;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
@RequiredArgsConstructor
@Slf4j
public class VideoListProcessor implements Processor {

    private final JobSender jobSender;
    private final JobRepository jobRepository;
    private final RequestInfoRepository requestInfoRepository;

    private final RequestHelper requestHelper;

    @Override
    public String accept(JobDto job) {
        jobRepository.updateStatus(job, JobStatus.IN_PROGRESS);
       try {
           RequestInfo requestInfo = requestInfoRepository.findOne(job.getId());
           Response response = requestHelper.videoList(requestInfo.getText());
           YouTubeParser<List<String>> parser = new FirstResponseVideoListParser(response);

           List<String> videoIds = parser.parse(requestInfo.getVideoId());
           ContinuationInfo info = parser.parseContinuationData();
           log.info("Data and info parsed successfully for VideoList");

           int count = videoIds.size();
           videoIds.forEach(id -> sendJob(job.getId(), id, JobType.COMMENT, requestInfo.getCommentCount()));

           while (count <  requestInfo.getVideoCount()) {
               response = requestHelper.videoList(requestInfo.getText(), info);

               parser = new NextResponseVideoListParser(response);

               videoIds = parser.parse("");
               videoIds.forEach(id -> sendJob(job.getId(), id, JobType.COMMENT, requestInfo.getCommentCount()));

               count += videoIds.size();

               ContinuationInfo newInfo = parser.parseContinuationData();
               info = mergeContinuation(info, newInfo);
               log.info("Data and info parsed successfully for VideoList");
           }
       } catch (Exception e){
           log.error("LIST: ", e);
           return JobStatus.FAILED;
       }
        return JobStatus.COMPLETED;
    }

    private void sendJob(String parentId, String videoId, String jobType, int commentsCount){
        JobDto job = new JobDto(JobStatus.NEW, jobType);

        RequestInfo requestInfo = new RequestInfo();
        requestInfo.setVideoId(videoId);

        requestInfo.setCommentCount(commentsCount);
        job.setParentId(parentId);
        jobRepository.save(job);

        requestInfo.setId(job.getId());
        requestInfoRepository.save(requestInfo);

        jobSender.sendJob(job);
    }

    @Override
    public boolean canProcess(JobDto jobDto) {
        return JobType.VIDEO_ID.equals(jobDto.getType());
    }

    private ContinuationInfo mergeContinuation(ContinuationInfo main, ContinuationInfo next) {
        next.setSessionToken(main.getSessionToken());
        return next;
    }

}
